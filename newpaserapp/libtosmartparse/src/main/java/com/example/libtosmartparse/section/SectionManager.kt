package com.example.libtosmartparse.section


import com.example.libtosmartparse.packet.TsPacket


/**
 * @author xxm
 * 2022/7/17
 **/
abstract class SectionManager {
    private val SECTION_HEAD = 3
    private val MOD_COUNTER_LENGTH = 16
    private val PAYLOAD_VALID_LENGTH = 184
    private val POINT_FIELD_LENGTH = 1

    var mSectionListener: SectionListener? = null

    private var mSection: Section? = null
    private val mPidSections: HashMap<Int, Section> = HashMap()
    private val mPreCounter: HashMap<Int, Int> = HashMap()

    /**
     * 组成特定表
     *
     * @param commonSection 一般section
     * @param pid 包的pid
     */
    abstract fun composeSpecifiedSection(commonSection: Section?, pid: Int?)

    open fun getSection(tsPacket: TsPacket, specifiedTableId: Int) {
        if (tsPacket.getTransportErrorIndicator() == 1) {
            return
        }
        if (tsPacket.getAdaptationFieldControl() != 1) {
            return
        }
        val pid = tsPacket.getPid()
        val payload = tsPacket.getPayload()
        var preCounter: Int
        if (tsPacket.getPayloadUnitStartIndicator() == 1) {
            val pointerFieldValue = payload[0].toInt()
            val tableId = payload[POINT_FIELD_LENGTH + pointerFieldValue].toInt()
            if (tableId != specifiedTableId) {
                return
            }
            mSection = Section()
            val sectionHead = ByteArray(3)
            sectionHead[0] = payload[pointerFieldValue + 1]
            sectionHead[1] = payload[pointerFieldValue + 2]
            sectionHead[2] = payload[pointerFieldValue + 3]
            mSection!!.setHeadDate(sectionHead)
            composeSection(payload, pointerFieldValue)
            preCounter = tsPacket.getContinuityCounter()
        } else {
            if (mPidSections.containsKey(pid)) {
                mSection = mPidSections[pid]
                preCounter = mPreCounter[pid]!!
            } else {
                return
            }
            if ((preCounter + 1) % MOD_COUNTER_LENGTH == tsPacket.getContinuityCounter()) {
                composeSection(payload, 0)
                preCounter = tsPacket.getContinuityCounter()
            } else {
                mPidSections.remove(pid)
                mPreCounter.remove(pid)
                mSection = null
            }
        }
        if (mSection != null) {
            if (mSection!!.getSectionLength() == mSection!!.getCurrentLength()) {
                val completeSection: Section = mSection as Section
                mSection = null
                mPidSections.remove(pid)
                mPreCounter.remove(pid)
                composeSpecifiedSection(completeSection, pid)
            } else {
                mPidSections[pid] = mSection!!
                mPreCounter[pid] = preCounter
            }
        }
    }

    private fun composeSection(payload: ByteArray, headOffset: Int) {
        val overSection: Int
        val startCopyLocation: Int
        val startCopySectionContentLocation: Int
        val copyLength: Int
        if (mSection!!.getCurrentLength() == 0) {
            overSection =
                PAYLOAD_VALID_LENGTH - headOffset - POINT_FIELD_LENGTH - SECTION_HEAD -
                        mSection!!.getSectionLength()
            startCopyLocation = headOffset + POINT_FIELD_LENGTH + SECTION_HEAD
            startCopySectionContentLocation = 0
            copyLength = PAYLOAD_VALID_LENGTH - headOffset - POINT_FIELD_LENGTH - SECTION_HEAD
        } else {
            overSection =
                PAYLOAD_VALID_LENGTH + mSection!!.getCurrentLength() - mSection!!.getSectionLength()
            startCopyLocation = 0
            startCopySectionContentLocation = mSection!!.getCurrentLength()
            copyLength = PAYLOAD_VALID_LENGTH
        }
        if (overSection >= 0) {
            System.arraycopy(
                payload,
                startCopyLocation,
                mSection!!.getData(),
                startCopySectionContentLocation,
                mSection!!.getSectionLength() - mSection!!.getCurrentLength()
            )
            mSection!!.setCurrentLength(mSection!!.getSectionLength())
        } else {
            System.arraycopy(
                payload, startCopyLocation,
                mSection!!.getData(), startCopySectionContentLocation, copyLength
            )
            mSection!!.setCurrentLength(copyLength + mSection!!.getCurrentLength())
        }
    }

    open fun setListener(sectionListener: SectionListener?) {
        mSectionListener = sectionListener
    }
}
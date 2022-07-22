package com.example.libtosmartparse.section

import com.example.libtosmartparse.packet.TsPacket
import com.example.libtosmartparse.packet.shl
import com.example.libtosmartparse.packet.shr
import java.util.*
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramMapSectionManager : SectionManager(), Observer {
    private var mProgramMapSection: ProgramMapSection? = null
    private var mFilterPidList = ArrayList<Int>()
    fun setFilterPidList(filterPidList: ArrayList<Int>) {
        mFilterPidList = filterPidList
    }

    fun getFilterPidList(): ArrayList<Int> {
        return mFilterPidList
    }

    override fun composeSpecifiedSection(commonSection: Section?, pid: Int?) {
        if (commonSection == null) {
            return
        }
        if (commonSection.getCurrentLength() == commonSection.getSectionLength()) {
            val commonSectionContent = commonSection.getData()
            val programMapFilledHead = IntArray(10)
            programMapFilledHead[0] = commonSection.getTableId()
            programMapFilledHead[1] = commonSection.getReserved()
            programMapFilledHead[2] = commonSection.getSectionLength()
            programMapFilledHead[3] =
                commonSectionContent!![0] shl 8 or (commonSectionContent[1].toInt() and 0xFF)
            programMapFilledHead[4] = commonSectionContent[2] shr 1 and 0x1F
            programMapFilledHead[5] = (commonSectionContent[2] and 0x01).toInt()
            programMapFilledHead[6] = commonSectionContent[3].toInt()
            programMapFilledHead[7] = commonSectionContent[4].toInt()
            programMapFilledHead[8] =
                (commonSectionContent[5] and 0x1F) shl 8 or commonSectionContent[6].toInt()
            programMapFilledHead[9] =
                ((commonSectionContent[7] and 0x0F) shl 8) or (commonSectionContent[8].toInt())
            val programMapSection = ProgramMapSection()
            programMapSection.setHeadDate(programMapFilledHead)

            if (programMapSection.getSectionNumber() != 0 ||
                programMapSection.getLastSectionNumber() != 0
            ) {
                return
            }

            val programInfoLength = programMapSection.getProgramInfoLength()

            val programInfo = ByteArray(programInfoLength)
            System.arraycopy(
                commonSectionContent,
                PROGRAM_MAP_SPECIAL_HEAD_LENGTH,
                programInfo,
                0,
                programInfoLength
            )
            programMapSection.setDescriptor(programInfo)

            val componentStart = PROGRAM_MAP_SPECIAL_HEAD_LENGTH + programInfoLength
            val copyComponentLength = commonSectionContent.size - componentStart - CRC_CODE_LENGTH
            val componentContent = ByteArray(copyComponentLength)
            System.arraycopy(
                commonSectionContent,
                componentStart,
                componentContent,
                0,
                copyComponentLength
            )
            programMapSection.setData(componentContent)

            val crcStartLocation = commonSectionContent.size - CRC_CODE_LENGTH
            val crcCode = ByteArray(4)
            crcCode[0] = commonSectionContent[crcStartLocation]
            crcCode[1] = commonSectionContent[crcStartLocation + 1]
            crcCode[2] = commonSectionContent[crcStartLocation + 2]
            crcCode[3] = commonSectionContent[crcStartLocation + 3]
            programMapSection.setCrcCode(crcCode)
            programMapSection.setPid(pid!!)
            mProgramMapSection = programMapSection
            mFilterPidList.remove(pid)
            mSectionListener!!.makePmt(pid, mProgramMapSection)
        }
    }

    override fun update(observable: Observable?, o: Any) {
        val tsPacket: TsPacket = o as TsPacket
        if (!mFilterPidList.contains(tsPacket.getPid())) {
            return
        }
        getSection(tsPacket, SPECIFIED_PMT_TABLE_ID)
    }

    companion object {
        private const val PROGRAM_MAP_SPECIAL_HEAD_LENGTH = 9
        private const val CRC_CODE_LENGTH = 4
        private const val SPECIFIED_PMT_TABLE_ID = 2
    }
}

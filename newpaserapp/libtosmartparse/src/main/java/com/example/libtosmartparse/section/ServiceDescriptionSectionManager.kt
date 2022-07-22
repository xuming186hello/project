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
class ServiceDescriptionSectionManager : SectionManager(), Observer {
    private var mServiceDescriptionSection: ServiceDescriptionSection? = null

    override fun composeSpecifiedSection(commonSection: Section?, pid: Int?) {
        if (commonSection!!.getCurrentLength() == commonSection.getSectionLength()) {
            val commonSectionContent = commonSection.getData()
            val serviceDescriptionFilledHead = IntArray(9)
            serviceDescriptionFilledHead[0] = commonSection.getTableId()
            serviceDescriptionFilledHead[1] = commonSection.getReserved()
            serviceDescriptionFilledHead[2] = commonSection.getSectionLength()
            serviceDescriptionFilledHead[3] =
                commonSectionContent!![0] shl 8 or commonSectionContent[1].toInt() and 0xFF
            serviceDescriptionFilledHead[4] = commonSectionContent[2] shr 1 and 0x1F
            serviceDescriptionFilledHead[5] = (commonSectionContent[2] and 0x01).toInt()
            serviceDescriptionFilledHead[6] = commonSectionContent[3].toInt()
            serviceDescriptionFilledHead[7] = commonSectionContent[4].toInt()
            serviceDescriptionFilledHead[8] =
                (commonSectionContent[5] and 0xFF.toByte()) shl 8 or ((commonSectionContent[6]
                        and 0xFF.toByte()).toInt())
            val serviceDescriptionSection = ServiceDescriptionSection()
            serviceDescriptionSection.setHeadDate(serviceDescriptionFilledHead)

            val copyServiceLength =
                commonSectionContent.size - SERVICE_DESCRIPTION_SPECIAL_HEAD_LENGTH - CRC_CODE_LENGTH
            val serviceContent = ByteArray(copyServiceLength)
            System.arraycopy(
                commonSectionContent,
                SERVICE_DESCRIPTION_SPECIAL_HEAD_LENGTH,
                serviceContent,
                0,
                copyServiceLength
            )
            serviceDescriptionSection.setData(serviceContent)
            val crcStartLocation = commonSectionContent.size - CRC_CODE_LENGTH
            val crcCode = ByteArray(4)
            crcCode[0] = commonSectionContent[crcStartLocation]
            crcCode[1] = commonSectionContent[crcStartLocation + 1]
            crcCode[2] = commonSectionContent[crcStartLocation + 2]
            crcCode[3] = commonSectionContent[crcStartLocation + 3]
            serviceDescriptionSection.setCrcCode(crcCode)
            mServiceDescriptionSection = serviceDescriptionSection
            mSectionListener!!.makeSdt(mServiceDescriptionSection)
        }
    }

    override fun update(observable: Observable?, o: Any) {
        val tsPacket: TsPacket = o as TsPacket
        if (tsPacket.getPid() != SDT_PID) {
            return
        }
        getSection(tsPacket, SPECIFIED_SDT_TABLE_ID)
    }

    companion object {
        private const val SERVICE_DESCRIPTION_SPECIAL_HEAD_LENGTH = 8
        private const val CRC_CODE_LENGTH = 4
        private const val SPECIFIED_SDT_TABLE_ID = 0X42
        private const val SDT_PID = 0X11
    }
}


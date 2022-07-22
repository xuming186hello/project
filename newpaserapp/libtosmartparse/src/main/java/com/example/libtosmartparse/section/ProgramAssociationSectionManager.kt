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
class ProgramAssociationSectionManager : SectionManager(), Observer {
    private var mProgramAssociationSection: ProgramAssociationSection? = null

    override fun update(observable: Observable?, o: Any) {
        val tsPacket: TsPacket = o as TsPacket
        if (tsPacket.getPid() != PAT_PID) {
            return
        }
        getSection(tsPacket, SPECIFIED_PAT_TABLE_ID)
    }

    companion object {
        private const val PROGRAM_ASSOCIATION_SPECIAL_HEAD_LENGTH = 5
        private const val CRC_CODE_LENGTH = 4
        private const val SPECIFIED_PAT_TABLE_ID = 0
        private const val PAT_PID = 0
    }

    override fun composeSpecifiedSection(commonSection: Section?, pid: Int?) {
        if (commonSection!!.getCurrentLength() == commonSection.getSectionLength()) {
            val commonSectionContent = commonSection.getData()
            val programAssociationFilledHead = IntArray(8)
            programAssociationFilledHead[0] = commonSection.getTableId()
            programAssociationFilledHead[1] = commonSection.getReserved()
            programAssociationFilledHead[2] = commonSection.getSectionLength()
            programAssociationFilledHead[3] = commonSectionContent!![0] shl 8 or
                    commonSectionContent[1].toInt() and 0xFF
            programAssociationFilledHead[4] = commonSectionContent[2] shr 1 and 0x1F
            programAssociationFilledHead[5] = (commonSectionContent[2] and 0x01).toInt()
            programAssociationFilledHead[6] = commonSectionContent[3].toInt()
            programAssociationFilledHead[7] = commonSectionContent[4].toInt()
            val programAssociationSection = ProgramAssociationSection()
            programAssociationSection.setHeadDate(programAssociationFilledHead)
            val copyProgramLength =
                commonSectionContent.size - PROGRAM_ASSOCIATION_SPECIAL_HEAD_LENGTH - CRC_CODE_LENGTH
            val programContent = ByteArray(copyProgramLength)
            System.arraycopy(
                commonSectionContent,
                PROGRAM_ASSOCIATION_SPECIAL_HEAD_LENGTH,
                programContent,
                0,
                copyProgramLength
            )
            programAssociationSection.setData(programContent)
            val crcStartLocation = commonSectionContent.size - CRC_CODE_LENGTH
            val crcCode = ByteArray(4)

            crcCode[0] = commonSectionContent[crcStartLocation]
            crcCode[1] = commonSectionContent[crcStartLocation + 1]
            crcCode[2] = commonSectionContent[crcStartLocation + 2]
            crcCode[3] = commonSectionContent[crcStartLocation + 3]
            programAssociationSection.setCrcCode(crcCode)
            mProgramAssociationSection = programAssociationSection
            mSectionListener!!.makePat(mProgramAssociationSection)
        }
    }
}

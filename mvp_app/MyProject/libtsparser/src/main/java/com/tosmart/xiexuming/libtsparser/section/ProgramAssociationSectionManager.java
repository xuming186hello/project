package com.tosmart.xiexuming.libtsparser.section;


import com.tosmart.xiexuming.libtsparser.tspacket.TsPacket;

import java.util.Observable;
import java.util.Observer;


/**
 * @author xiexuming
 */
public class ProgramAssociationSectionManager extends SectionManager implements Observer {
    private static final int PROGRAM_ASSOCIATION_SPECIAL_HEAD_LENGTH = 5;
    private static final int CRC_CODE_LENGTH = 4;
    private static final int SPECIFIED_PAT_TABLE_ID = 0;
    private static final int PAT_PID = 0;

    private ProgramAssociationSection mProgramAssociationSection;

    @Override
    public void composeSpecifiedSection(Section commonSection, Integer pid) {
        if (commonSection.getCurrentLength() == commonSection.getSectionLength()) {
            byte[] commonSectionContent = commonSection.getData();
            int[] programAssociationFilledHead = new int[8];
            programAssociationFilledHead[0] = commonSection.getTableId();
            programAssociationFilledHead[1] = commonSection.getReserved();
            programAssociationFilledHead[2] = commonSection.getSectionLength();
            programAssociationFilledHead[3] = commonSectionContent[0] << 8 | commonSectionContent[1] & 0xFF;
            programAssociationFilledHead[4] = commonSectionContent[2] >> 1 & 0x1F;
            programAssociationFilledHead[5] = commonSectionContent[2] & 0x01;
            programAssociationFilledHead[6] = commonSectionContent[3];
            programAssociationFilledHead[7] = commonSectionContent[4];
            ProgramAssociationSection programAssociationSection = new ProgramAssociationSection();
            programAssociationSection.setHeadDate(programAssociationFilledHead);

            int copyProgramLength = commonSectionContent.length - PROGRAM_ASSOCIATION_SPECIAL_HEAD_LENGTH - CRC_CODE_LENGTH;
            byte[] programContent = new byte[copyProgramLength];
            System.arraycopy(commonSectionContent, PROGRAM_ASSOCIATION_SPECIAL_HEAD_LENGTH, programContent, 0, copyProgramLength);
            programAssociationSection.setData(programContent);

            int crcStartLocation = commonSectionContent.length - CRC_CODE_LENGTH;
            byte[] crcCode = new byte[4];
            crcCode[0] = commonSectionContent[crcStartLocation];
            crcCode[1] = commonSectionContent[crcStartLocation + 1];
            crcCode[2] = commonSectionContent[crcStartLocation + 2];
            crcCode[3] = commonSectionContent[crcStartLocation + 3];
            programAssociationSection.setCrcCode(crcCode);

            mProgramAssociationSection = programAssociationSection;
            mSectionListener.makePat(mProgramAssociationSection);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        TsPacket tsPacket = (TsPacket) o;
        if (tsPacket.getPid() != PAT_PID) {
            return;
        }
        getSection(tsPacket, SPECIFIED_PAT_TABLE_ID);
    }
}

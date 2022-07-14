package com.tosmart.xiexuming.libtsparser.section;


import com.tosmart.xiexuming.libtsparser.tspacket.TsPacket;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * @author xiexuming
 */
public class ProgramMapSectionManager extends SectionManager implements Observer {
    private static final int PROGRAM_MAP_SPECIAL_HEAD_LENGTH = 9;
    private static final int CRC_CODE_LENGTH = 4;
    private static final int SPECIFIED_PMT_TABLE_ID = 2;

    private ProgramMapSection mProgramMapSection;
    private ArrayList<Integer> mFilterPidList = new ArrayList<>();

    public void setFilterPidList(ArrayList<Integer> filterPidList) {
        this.mFilterPidList = filterPidList;
    }

    public ArrayList<Integer> getFilterPidList() {
        return mFilterPidList;
    }

    @Override
    public void composeSpecifiedSection(Section commonSection, Integer pid) {
        if (commonSection == null) {
            return;
        }
        if (commonSection.getCurrentLength() == commonSection.getSectionLength()) {
            byte[] commonSectionContent = commonSection.getData();
            int[] programMapFilledHead = new int[10];
            programMapFilledHead[0] = commonSection.getTableId();
            programMapFilledHead[1] = commonSection.getReserved();
            programMapFilledHead[2] = commonSection.getSectionLength();
            programMapFilledHead[3] = commonSectionContent[0] << 8 | commonSectionContent[1] & 0xFF;
            programMapFilledHead[4] = commonSectionContent[2] >> 1 & 0x1F;
            programMapFilledHead[5] = commonSectionContent[2] & 0x01;
            programMapFilledHead[6] = commonSectionContent[3];
            programMapFilledHead[7] = commonSectionContent[4];
            programMapFilledHead[8] = (commonSectionContent[5] & 0x1F) << 8 | commonSectionContent[6];
            programMapFilledHead[9] = (commonSectionContent[7] & 0x0F) << 8 | commonSectionContent[8] & 0xFF;
            ProgramMapSection programMapSection = new ProgramMapSection();
            programMapSection.setHeadDate(programMapFilledHead);

            if (programMapSection.getSectionNumber() != 0 || programMapSection.getLastSectionNumber() != 0) {
                return;
            }
            int programInfoLength = programMapSection.getProgramInfoLength();
            byte[] programInfo = new byte[programInfoLength];
            System.arraycopy(commonSectionContent, PROGRAM_MAP_SPECIAL_HEAD_LENGTH, programInfo, 0, programInfoLength);
            programMapSection.setDescriptor(programInfo);

            int componentStart = PROGRAM_MAP_SPECIAL_HEAD_LENGTH + programInfoLength;
            int copyComponentLength = commonSectionContent.length - componentStart - CRC_CODE_LENGTH;
            byte[] componentContent = new byte[copyComponentLength];
            System.arraycopy(commonSectionContent, componentStart, componentContent, 0, copyComponentLength);
            programMapSection.setData(componentContent);

            int crcStartLocation = commonSectionContent.length - CRC_CODE_LENGTH;
            byte[] crcCode = new byte[4];
            crcCode[0] = commonSectionContent[crcStartLocation];
            crcCode[1] = commonSectionContent[crcStartLocation + 1];
            crcCode[2] = commonSectionContent[crcStartLocation + 2];
            crcCode[3] = commonSectionContent[crcStartLocation + 3];
            programMapSection.setCrcCode(crcCode);
            programMapSection.setPid(pid);

            mProgramMapSection = programMapSection;
            mFilterPidList.remove(pid);
            mSectionListener.makePmt(pid, mProgramMapSection);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        TsPacket tsPacket = (TsPacket) o;
        if (!mFilterPidList.contains(tsPacket.getPid())) {
            return;
        }
        getSection(tsPacket, SPECIFIED_PMT_TABLE_ID);
    }
}

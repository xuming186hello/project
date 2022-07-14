package com.tosmart.xiexuming.libtsparser.table;

import com.tosmart.xiexuming.libtsparser.program.Program;
import com.tosmart.xiexuming.libtsparser.section.ProgramAssociationSection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xiexuming
 */
public class ProgramAssociationTableManager {
    private static final int PROGRAM_NUMBER_LENGTH = 2;
    private static final int PROGRAM_LENGTH = 4;
    private static final int FILL_CHARACTER = 0xFFFFFFFF;

    private final ArrayList<Integer> mProgramPidList = new ArrayList<>();
    private final HashMap<Integer, ProgramAssociationSection> mSections = new HashMap<>();
    private int mVersionNumber = -1;

    public boolean composeSection(ProgramAssociationSection programAssociationSection) {
        if (programAssociationSection == null) {
            return false;
        }
        int lastSectionNumber = programAssociationSection.getLastSectionNumber();
        if (mVersionNumber != -1) {
            if (mVersionNumber != programAssociationSection.getVersionNumber()) {
                mVersionNumber = -1;
                mSections.clear();
                return false;
            }
            if (mSections.containsKey(programAssociationSection.getVersionNumber())) {
                return false;
            }
        }
        mSections.put(programAssociationSection.getSectionNumber(), programAssociationSection);
        mVersionNumber = programAssociationSection.getVersionNumber();
        return mSections.size() == (lastSectionNumber + 1);
    }

    public ProgramAssociationTable getProgramAssociationTable() {
        ProgramAssociationTable mProgramAssociationTable = new ProgramAssociationTable();
        ArrayList<Program> programList = new ArrayList<>();
        for (int i = 0; i < mSections.size(); i++) {
            ProgramAssociationSection programAssociationSection = mSections.get(i);
            byte[] programData = programAssociationSection != null ? programAssociationSection.getData() : null;
            if (programData == null) {
                continue;
            }
            ArrayList<Program> programs = composePrograms(programData);
            if (programs == null) {
                continue;
            }
            programList.addAll(programs);
        }
        mProgramAssociationTable.setPrograms(programList);
        mProgramAssociationTable.setVersion(mVersionNumber);
        return mProgramAssociationTable;
    }

    private ArrayList<Program> composePrograms(byte[] programData) {
        if (programData.length <= 0) {
            return null;
        }
        ArrayList<Program> programList = new ArrayList<>();
        for (int j = 0; j < programData.length; j = j + PROGRAM_LENGTH) {
            Program program = new Program();
            int programNumber = programData[j] << 8 | programData[j + 1] & 0xFF;
            program.setProgramNumber(programNumber);
            int programMapPid = (programData[j + PROGRAM_NUMBER_LENGTH] & 0x1F) << 8 | (programData[j + PROGRAM_NUMBER_LENGTH + 1] & 0xFF);
            if (programNumber == 0) {
                program.setNetworkId(programMapPid);
                program.setProgramMapPid(FILL_CHARACTER);
            } else {
                program.setProgramMapPid(programMapPid);
                program.setNetworkId(FILL_CHARACTER);
                mProgramPidList.add(programMapPid);
            }
            programList.add(program);
        }
        return programList;
    }

    public ArrayList<Integer> getProgramPidList() {
        return mProgramPidList;
    }
}

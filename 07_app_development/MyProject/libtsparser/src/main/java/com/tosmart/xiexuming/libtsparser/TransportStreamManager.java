package com.tosmart.xiexuming.libtsparser;

import com.tosmart.xiexuming.libtsparser.program.Program;
import com.tosmart.xiexuming.libtsparser.program.ProgramManager;
import com.tosmart.xiexuming.libtsparser.section.ProgramAssociationSection;
import com.tosmart.xiexuming.libtsparser.section.ProgramAssociationSectionManager;
import com.tosmart.xiexuming.libtsparser.section.ProgramMapSection;
import com.tosmart.xiexuming.libtsparser.section.ProgramMapSectionManager;
import com.tosmart.xiexuming.libtsparser.section.SectionListener;
import com.tosmart.xiexuming.libtsparser.section.ServiceDescriptionSection;
import com.tosmart.xiexuming.libtsparser.section.ServiceDescriptionSectionManager;
import com.tosmart.xiexuming.libtsparser.table.ProgramAssociationTable;
import com.tosmart.xiexuming.libtsparser.table.ProgramAssociationTableManager;
import com.tosmart.xiexuming.libtsparser.table.ProgramMapTable;
import com.tosmart.xiexuming.libtsparser.table.ProgramMapTableManager;
import com.tosmart.xiexuming.libtsparser.table.ServiceDescriptionTable;
import com.tosmart.xiexuming.libtsparser.table.ServiceDescriptionTableManager;
import com.tosmart.xiexuming.libtsparser.tspacket.TsPacketManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author xiexuming
 */
public class TransportStreamManager {
    ProgramAssociationSectionManager mProgramAssociationSectionManager;
    ProgramMapSectionManager mProgramMapSectionManager;
    ServiceDescriptionSectionManager mServiceDescriptionSectionManager;

    ProgramAssociationTableManager mProgramAssociationTableManager;
    ProgramMapTableManager mProgramMapTableManager;
    ServiceDescriptionTableManager mServiceDescriptionTableManager;

    HashMap<Integer, ProgramMapTable> mProgramMapTableMap;
    ServiceDescriptionTable mServiceDescriptionTable;
    ProgramAssociationTable mProgramAssociationTable;
    TsPacketManager mPacketManager;

    public TransportStreamManager() {
        mPacketManager = TsPacketManager.getInstance();
        mProgramMapTableMap = new HashMap<>();
        mServiceDescriptionTableManager = new ServiceDescriptionTableManager();
        mProgramAssociationSectionManager = new ProgramAssociationSectionManager();
        mProgramMapSectionManager = new ProgramMapSectionManager();
        mServiceDescriptionSectionManager = new ServiceDescriptionSectionManager();
        mProgramAssociationTableManager = new ProgramAssociationTableManager();
        mProgramMapTableManager = new ProgramMapTableManager();
    }

    public List<Program> parseTsStream(String filePath) {
        try {
            int tsPacketLength = mPacketManager.getTsPacketLength(filePath);
            System.out.println(tsPacketLength);
            if (tsPacketLength <= 0) {
                return null;
            }
            SectionListener sectionListener = new SectionListener() {
                @Override
                public void makePat(ProgramAssociationSection programAssociationSection) {
                    boolean programAssociationComposeFinish = mProgramAssociationTableManager.composeSection(programAssociationSection);
                    if (programAssociationComposeFinish) {
                        mProgramAssociationTable =
                                mProgramAssociationTableManager.getProgramAssociationTable();
                        ArrayList<Integer> programPidList = mProgramAssociationTableManager.getProgramPidList();
                        mProgramMapSectionManager.setFilterPidList(programPidList);
                        mPacketManager.deleteObserver(mProgramAssociationSectionManager);
                    }
                }

                @Override
                public void makePmt(int pid, ProgramMapSection programMapSection) {
                    ProgramMapTable programMapTable = mProgramMapTableManager.getProgramMapTable(programMapSection, pid);
                    mProgramMapTableMap.put(pid, programMapTable);
                    ArrayList<Integer> filterPidList = mProgramMapSectionManager.getFilterPidList();
                    if (filterPidList.size() <= 0) {
                        mPacketManager.deleteObserver(mProgramMapSectionManager);
                    }
                }

                @Override
                public void makeSdt(ServiceDescriptionSection serviceDescriptionSection) {
                    boolean serviceDescriptionComposeFinish = mServiceDescriptionTableManager.composeSection(serviceDescriptionSection);
                    if (serviceDescriptionComposeFinish) {
                        mServiceDescriptionTable = mServiceDescriptionTableManager.getServiceDescriptionTable();
                        mPacketManager.deleteObserver(mServiceDescriptionSectionManager);
                    }
                }
            };

            mProgramAssociationSectionManager.setListener(sectionListener);
            mProgramMapSectionManager.setListener(sectionListener);
            mServiceDescriptionSectionManager.setListener(sectionListener);

            mPacketManager.addObserver(mProgramMapSectionManager);
            mPacketManager.addObserver(mProgramAssociationSectionManager);
            mPacketManager.addObserver(mServiceDescriptionSectionManager);
            mPacketManager.distributePacket(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ProgramManager.getInstance().getProgramList(mProgramAssociationTable, mServiceDescriptionTable, mProgramMapTableMap);
    }

    public void forceClose() {
        mPacketManager.deleteObservers();
    }
}

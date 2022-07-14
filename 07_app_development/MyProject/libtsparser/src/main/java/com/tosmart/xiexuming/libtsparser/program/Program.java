package com.tosmart.xiexuming.libtsparser.program;

import androidx.annotation.NonNull;

import com.tosmart.xiexuming.libtsparser.table.ProgramMapTable;

/**
 * @author xiexuming
 */
public class Program {
    private int programNumber;
    private int programMapPid;
    private int networkId;
    private String programName;
    private ProgramMapTable programMapTable;

    @NonNull
    @Override
    public String toString() {
        return "Program{" +
                "programNumber=0x" + Integer.toHexString(programNumber) +
                ", programMapPid=0x" + Integer.toHexString(programMapPid) +
                ", networkId=0x" + Integer.toHexString(networkId) +
                ", programName='" + programName + '\'' +
                " " + "\n"
                + programMapTable
                + '}'
                + "\n";
    }

    public ProgramMapTable getProgramMapTable() {
        return programMapTable;
    }

    public void setProgramMapTable(ProgramMapTable programMapTable) {
        this.programMapTable = programMapTable;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(int programNumber) {
        this.programNumber = programNumber;
    }

    public int getProgramMapPid() {
        return programMapPid;
    }

    public void setProgramMapPid(int programMapPid) {
        this.programMapPid = programMapPid;
    }
}
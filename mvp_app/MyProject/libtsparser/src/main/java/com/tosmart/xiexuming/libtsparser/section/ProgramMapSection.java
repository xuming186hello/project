package com.tosmart.xiexuming.libtsparser.section;


import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * @author xiexuming
 */
public class ProgramMapSection extends Section {
    int pid;
    int programNumber;
    int versionNumber;
    int currentNextIndicator;
    int sectionNumber;
    int lastSectionNumber;
    int PCR_PID;
    int programInfoLength;
    private byte[] descriptor;
    private byte[] crcCode;

    @NonNull
    @Override
    public String toString() {
        return "ProgramMapSection{" +
                "Pid=" + pid +
                ", tableId=" + getTableId() +
                ", sectionLength=" + getSectionLength() +
                ", programNumber=" + programNumber +
                ", versionNumber=" + versionNumber +
                ", currentNextIndicator=" + currentNextIndicator +
                ", sectionNumber=" + sectionNumber +
                ", lastSectionNumber=" + lastSectionNumber +
                ", PCR_PID=" + PCR_PID +
                ", programInfoLength=" + programInfoLength +
                ", descriptor=" + Arrays.toString(descriptor) +
                ", data=" + Arrays.toString(getData()) +
                ", crcCode=" + Arrays.toString(crcCode) +
                '}';
    }

    public void setHeadDate(int[] programMapSectionHead) {
        setTableId(programMapSectionHead[0]);
        setReserved(programMapSectionHead[1]);
        setSectionLength(programMapSectionHead[2]);
        programNumber = programMapSectionHead[3];
        versionNumber = programMapSectionHead[4];
        currentNextIndicator = programMapSectionHead[5];
        sectionNumber = programMapSectionHead[6];
        lastSectionNumber = programMapSectionHead[7];
        PCR_PID = programMapSectionHead[8];
        programInfoLength = programMapSectionHead[9];
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(int programNumber) {
        this.programNumber = programNumber;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getCurrentNextIndicator() {
        return currentNextIndicator;
    }

    public void setCurrentNextIndicator(int currentNextIndicator) {
        this.currentNextIndicator = currentNextIndicator;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getLastSectionNumber() {
        return lastSectionNumber;
    }

    public void setLastSectionNumber(int lastSectionNumber) {
        this.lastSectionNumber = lastSectionNumber;
    }

    public int getPCR_PID() {
        return PCR_PID;
    }

    public void setPCR_PID(int PCR_PID) {
        this.PCR_PID = PCR_PID;
    }

    public int getProgramInfoLength() {
        return programInfoLength;
    }

    public void setProgramInfoLength(int programInfoLength) {
        this.programInfoLength = programInfoLength;
    }

    public byte[] getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(byte[] crcCode) {
        this.crcCode = crcCode;
    }

    public byte[] getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(byte[] descriptor) {
        this.descriptor = descriptor;
    }
}

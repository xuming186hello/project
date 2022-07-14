package com.tosmart.xiexuming.libtsparser.section;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * @author xiexuming
 */
public class ProgramAssociationSection extends Section {
    private int transportStreamId;
    private int versionNumber;
    private int currentNextIndicator;
    private int sectionNumber;
    private int lastSectionNumber;
    private byte[] crcCode;

    public void setHeadDate(int[] programAssociationSectionHead) {
        setTableId(programAssociationSectionHead[0]);
        setReserved(programAssociationSectionHead[1]);
        setSectionLength(programAssociationSectionHead[2]);
        transportStreamId = programAssociationSectionHead[3];
        versionNumber = programAssociationSectionHead[4];
        currentNextIndicator = programAssociationSectionHead[5];
        sectionNumber = programAssociationSectionHead[6];
        lastSectionNumber = programAssociationSectionHead[7];
    }

    @NonNull
    @Override
    public String toString() {
        return "ProgramAssociationSection{" +
                "tableId=" + getTableId() +
                ", sectionLength=" + getSectionLength() +
                ", sectionSyntaxIndicator=" + getSectionSyntaxIndicator() +
                ", reserved=" + getReserved() +
                "transportStreamId=" + transportStreamId +
                ", versionNumber=" + versionNumber +
                ", currentNextIndicator=" + currentNextIndicator +
                ", sectionNumber=" + sectionNumber +
                ", lastSectionNumber=" + lastSectionNumber +
                ", crcCode=" + crcCode[0] + " " + crcCode[1] + " " + crcCode[2] + " " + crcCode[3] +
                ", data=" + Arrays.toString(getData()) +
                '}';
    }

    @Override
    public void setData(byte[] data) {
        super.setData(data);
    }

    private void setProgramContent(byte[] programContent) {
        setData(programContent);
    }

    public int getTransportStreamId() {
        return transportStreamId;
    }

    public void setTransportStreamId(int transportStreamId) {
        this.transportStreamId = transportStreamId;
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

    public byte[] getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(byte[] crcCode) {
        this.crcCode = crcCode;
    }
}

package com.tosmart.xiexuming.libtsparser.section;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * @author xiexuming
 */
public class Section {
    private int tableId;
    private int sectionLength;
    private int currentLength;
    private int sectionSyntaxIndicator;
    private int reserved;
    private byte[] data;

    @NonNull
    @Override
    public String toString() {
        return "Section{" +
                "tableId=" + tableId +
                ", sectionLength=" + sectionLength +
                ", currentLength=" + currentLength +
                ", sectionSyntaxIndicator=" + sectionSyntaxIndicator +
                ", reserved=" + reserved +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public int getSectionSyntaxIndicator() {
        return sectionSyntaxIndicator;
    }

    public void setSectionSyntaxIndicator(int sectionSyntaxIndicator) {
        this.sectionSyntaxIndicator = sectionSyntaxIndicator;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public void setHeadDate(byte[] sectionHead) {
        this.tableId = sectionHead[0];
        this.sectionSyntaxIndicator = (sectionHead[1] >> 8) & 0x01;
        this.reserved = (sectionHead[1] >> 4) & 0x03;
        this.sectionLength = (sectionHead[1] & 0x0F) << 8 | sectionHead[2] & 0xFF;
        this.data = new byte[sectionLength];
    }

    public byte[] getData() {
        return data;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getSectionLength() {
        return sectionLength;
    }

    public void setSectionLength(int sectionLength) {
        this.sectionLength = sectionLength;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

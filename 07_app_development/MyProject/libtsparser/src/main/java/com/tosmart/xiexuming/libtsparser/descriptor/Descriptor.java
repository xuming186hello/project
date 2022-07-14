package com.tosmart.xiexuming.libtsparser.descriptor;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * @author xiexuming
 */
public class Descriptor {
    public int descriptorTag;
    public int descriptorLength;
    private byte[] descriptorBuff;

    @NonNull
    @Override
    public String toString() {
        return "Descriptor{" +
                "descriptorTag=" + descriptorTag +
                ", descriptorLength=" + descriptorLength +
                ", descriptorBuff=" + Arrays.toString(descriptorBuff) +
                '}';
    }

    public int getDescriptorTag() {
        return descriptorTag;
    }

    public void setDescriptorTag(int descriptorTag) {
        this.descriptorTag = descriptorTag;
    }

    public int getDescriptorLength() {
        return descriptorLength;
    }

    public void setDescriptorLength(int descriptorLength) {
        this.descriptorLength = descriptorLength;
    }

    public byte[] getDescriptorBuff() {
        return descriptorBuff;
    }

    public void setDescriptorBuff(byte[] descriptorBuff) {
        this.descriptorBuff = descriptorBuff;
    }
}

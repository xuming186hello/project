package com.tosmart.xiexuming.libtsparser.table;

import androidx.annotation.NonNull;

import com.tosmart.xiexuming.libtsparser.descriptor.Descriptor;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class Component {
    private int streamType;
    private int elementaryPid;
    private int elementStreamInfoLength;
    private ArrayList<Descriptor> descriptor;

    @NonNull
    @Override
    public String toString() {
        return "Component{" +
                "streamType=" + streamType +
                ", elementaryPID=" + elementaryPid +
                ", ESInfoLength=" + elementStreamInfoLength +
                ", descriptor=" + descriptor +
                '}';
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public int getElementaryPid() {
        return elementaryPid;
    }

    public void setElementaryPid(int elementaryPid) {
        this.elementaryPid = elementaryPid;
    }

    public int getElementStreamInfoLength() {
        return elementStreamInfoLength;
    }

    public void setElementStreamInfoLength(int elementStreamInfoLength) {
        this.elementStreamInfoLength = elementStreamInfoLength;
    }

    public ArrayList<Descriptor> getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(ArrayList<Descriptor> descriptor) {
        this.descriptor = descriptor;
    }
}

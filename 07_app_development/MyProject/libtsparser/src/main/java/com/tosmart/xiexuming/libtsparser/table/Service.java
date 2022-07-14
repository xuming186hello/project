package com.tosmart.xiexuming.libtsparser.table;


import androidx.annotation.NonNull;

import com.tosmart.xiexuming.libtsparser.descriptor.Descriptor;

import java.util.ArrayList;


/**
 * @author xiexuming
 */
public class Service {
    private int serviceId;
    private int EITScheduleFlag;
    private int EITPresentFollowingFlag;
    private int runningStatus;
    private int freeCAMode;
    private int descriptorsLoopLength;
    private ArrayList<Descriptor> Descriptor;

    @NonNull
    @Override
    public String toString() {
        return "Service{" +
                "serviceId=0x" + Integer.toHexString(serviceId) +
                ", EITScheduleFlag=" + EITScheduleFlag +
                ", EITPresentFollowingFlag=" + EITPresentFollowingFlag +
                ", runningStatus=" + runningStatus +
                ", freeCAMode=" + freeCAMode +
                ", descriptorsLoopLength=" + descriptorsLoopLength +
                ", serviceDescriptor=" + Descriptor +
                '}';
    }

    public ArrayList<com.tosmart.xiexuming.libtsparser.descriptor.Descriptor> getDescriptor() {
        return Descriptor;
    }

    public void setDescriptor(ArrayList<com.tosmart.xiexuming.libtsparser.descriptor.Descriptor> descriptor) {
        Descriptor = descriptor;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getEITScheduleFlag() {
        return EITScheduleFlag;
    }

    public void setEITScheduleFlag(int EITScheduleFlag) {
        this.EITScheduleFlag = EITScheduleFlag;
    }

    public int getEITPresentFollowingFlag() {
        return EITPresentFollowingFlag;
    }

    public void setEITPresentFollowingFlag(int EITPresentFollowingFlag) {
        this.EITPresentFollowingFlag = EITPresentFollowingFlag;
    }

    public int getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(int runningStatus) {
        this.runningStatus = runningStatus;
    }

    public int getFreeCAMode() {
        return freeCAMode;
    }

    public void setFreeCAMode(int freeCAMode) {
        this.freeCAMode = freeCAMode;
    }

    public int getDescriptorsLoopLength() {
        return descriptorsLoopLength;
    }

    public void setDescriptorsLoopLength(int descriptorsLoopLength) {
        this.descriptorsLoopLength = descriptorsLoopLength;
    }
}

package com.example.libtosmartparse.table

import com.example.libtosmartparse.descriptor.Descriptor

/**
 * @author xxm
 * 2022/7/17
 **/
class Service {
    private var serviceId = 0
    private var EITScheduleFlag = 0
    private var EITPresentFollowingFlag = 0
    private var runningStatus = 0
    private var freeCAMode = 0
    private var descriptorsLoopLength = 0
    private var Descriptor: ArrayList<Descriptor>? = null
    override fun toString(): String {
        return "Service{" +
                "serviceId=0x" + Integer.toHexString(serviceId) +
                ", EITScheduleFlag=" + EITScheduleFlag +
                ", EITPresentFollowingFlag=" + EITPresentFollowingFlag +
                ", runningStatus=" + runningStatus +
                ", freeCAMode=" + freeCAMode +
                ", descriptorsLoopLength=" + descriptorsLoopLength +
                ", serviceDescriptor=" + Descriptor +
                '}'
    }

    fun getDescriptor(): ArrayList<Descriptor>? {
        return Descriptor
    }

    fun setDescriptor(descriptor: ArrayList<Descriptor>?) {
        Descriptor = descriptor
    }

    fun getServiceId(): Int {
        return serviceId
    }

    fun setServiceId(serviceId: Int) {
        this.serviceId = serviceId
    }

    fun getEITScheduleFlag(): Int {
        return EITScheduleFlag
    }

    fun setEITScheduleFlag(EITScheduleFlag: Int) {
        this.EITScheduleFlag = EITScheduleFlag
    }

    fun getEITPresentFollowingFlag(): Int {
        return EITPresentFollowingFlag
    }

    fun setEITPresentFollowingFlag(EITPresentFollowingFlag: Int) {
        this.EITPresentFollowingFlag = EITPresentFollowingFlag
    }

    fun getRunningStatus(): Int {
        return runningStatus
    }

    fun setRunningStatus(runningStatus: Int) {
        this.runningStatus = runningStatus
    }

    fun getFreeCAMode(): Int {
        return freeCAMode
    }

    fun setFreeCAMode(freeCAMode: Int) {
        this.freeCAMode = freeCAMode
    }

    fun getDescriptorsLoopLength(): Int {
        return descriptorsLoopLength
    }

    fun setDescriptorsLoopLength(descriptorsLoopLength: Int) {
        this.descriptorsLoopLength = descriptorsLoopLength
    }
}

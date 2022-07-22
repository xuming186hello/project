package com.example.libtosmartparse.table

import com.example.libtosmartparse.descriptor.Descriptor

/**
 * @author xxm
 * 2022/7/17
 **/
class Component {
    private var streamType = 0
    private var elementaryPid = 0
    private var elementStreamInfoLength = 0
    private var descriptor: ArrayList<Descriptor>? = null
    override fun toString(): String {
        return "Component{" +
                "streamType=" + streamType +
                ", elementaryPID=" + elementaryPid +
                ", ESInfoLength=" + elementStreamInfoLength +
                ", descriptor=" + descriptor +
                '}'
    }

    fun getStreamType(): Int {
        return streamType
    }

    fun setStreamType(streamType: Int) {
        this.streamType = streamType
    }

    fun getElementaryPid(): Int {
        return elementaryPid
    }

    fun setElementaryPid(elementaryPid: Int) {
        this.elementaryPid = elementaryPid
    }

    fun getElementStreamInfoLength(): Int {
        return elementStreamInfoLength
    }

    fun setElementStreamInfoLength(elementStreamInfoLength: Int) {
        this.elementStreamInfoLength = elementStreamInfoLength
    }

    fun getDescriptor(): ArrayList<Descriptor>? {
        return descriptor
    }

    fun setDescriptor(descriptor: ArrayList<Descriptor>?) {
        this.descriptor = descriptor
    }
}
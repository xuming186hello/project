package com.example.libtosmartparse.descriptor

import java.util.*

/**
 * @author xxm
 * 2022/7/17
 **/
open class Descriptor {
    private var descriptorTag = 0
    private var descriptorLength = 0
    private lateinit var descriptorBuff: ByteArray

    override fun toString(): String {
        return "Descriptor{" +
                "descriptorTag=" + descriptorTag +
                ", descriptorLength=" + descriptorLength +
                ", descriptorBuff=" + Arrays.toString(descriptorBuff) +
                '}'
    }

    fun getDescriptorTag(): Int {
        return descriptorTag
    }

    fun setDescriptorTag(descriptorTag: Int) {
        this.descriptorTag = descriptorTag
    }

    fun getDescriptorLength(): Int {
        return descriptorLength
    }

    fun setDescriptorLength(descriptorLength: Int) {
        this.descriptorLength = descriptorLength
    }

    fun getDescriptorBuff(): ByteArray {
        return descriptorBuff
    }

    fun setDescriptorBuff(descriptorBuff: ByteArray) {
        this.descriptorBuff = descriptorBuff
    }
}

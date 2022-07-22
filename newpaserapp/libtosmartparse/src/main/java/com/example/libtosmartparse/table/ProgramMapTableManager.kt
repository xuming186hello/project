package com.example.libtosmartparse.table

import ProgramMapTable
import com.example.libtosmartparse.descriptor.Descriptor
import com.example.libtosmartparse.descriptor.DescriptorManager
import com.example.libtosmartparse.packet.or
import com.example.libtosmartparse.packet.shl
import com.example.libtosmartparse.section.ProgramMapSection
import java.lang.Math.abs
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramMapTableManager {
    fun getProgramMapTable(programMapSection: ProgramMapSection, pid: Int): ProgramMapTable {
        val programMapTable = ProgramMapTable()
        programMapTable.setPid(pid)
        val programDescriptorContent: ByteArray = programMapSection.getDescriptor()
        val programDescriptor: ArrayList<Descriptor>? =
            DescriptorManager.composeDescriptor(programDescriptorContent)
        programMapTable.setProgramInfo(programDescriptor)
        val elementStreamContent: ByteArray? = programMapSection.getData()
        val components = elementStreamContent?.let { composeComponents(it) }
        programMapTable.setComponents(components)
        return programMapTable
    }

    fun composeComponents(elementStreamContent: ByteArray): ArrayList<Component>? {
        if (elementStreamContent.size <= 0) {
            return null
        }
        val componentList = ArrayList<Component>()
        var i = 0
        while (i < elementStreamContent.size - 1) {
            val component = Component()
            val streamType = elementStreamContent[i].toInt()
            component.setElementaryPid(streamType)
            val elementaryPid: Int =
                abs(elementStreamContent[i + 1] and 0x1F shl 8 or elementStreamContent[i + 2].toInt())
            component.setElementaryPid(elementaryPid)
            val elementStreamInfoLength: Int =
                abs(elementStreamContent[i + 3] and 0x0F or elementStreamContent[i + 4])
            component.setElementStreamInfoLength(elementStreamInfoLength)
            val esDescriptorContent = ByteArray(elementStreamInfoLength)
            val elementStreamContentStart = i + ELEMENT_STREAM_HEAD_LENGTH
            if ((i + elementStreamInfoLength + ELEMENT_STREAM_HEAD_LENGTH)
                > elementStreamContent.size - 1
            ) {
                break
            }
            System.arraycopy(
                elementStreamContent,
                elementStreamContentStart,
                esDescriptorContent,
                0,
                elementStreamInfoLength
            )
            val esDescriptor: ArrayList<Descriptor>? =
                DescriptorManager.composeDescriptor(esDescriptorContent)
            component.setDescriptor(esDescriptor)
            componentList.add(component)
            i += elementStreamInfoLength + ELEMENT_STREAM_HEAD_LENGTH
        }
        return componentList
    }

    companion object {
        private const val ELEMENT_STREAM_HEAD_LENGTH = 5
    }
}

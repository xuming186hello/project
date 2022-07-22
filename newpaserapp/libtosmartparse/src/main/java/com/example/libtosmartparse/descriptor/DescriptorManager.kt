package com.example.libtosmartparse.descriptor

import com.example.libtosmartparse.table.CharacterTable
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
class DescriptorManager : DescriptorExtension {
    override fun getServiceDescriptor(descriptor: Descriptor?): ServiceDescriptor? {
        if (descriptor == null) {
            return null
        }
        val descriptorTag = descriptor.getDescriptorTag()
        if (descriptorTag != SERVICE_DESCRIPTOR_TAG) {
            return null
        }
        val descriptorLength = descriptor.getDescriptorLength()
        if (descriptorLength <= 0) {
            return null
        }
        val descriptorBuff = descriptor.getDescriptorBuff() ?: return null
        val serviceDescriptor = ServiceDescriptor()
        serviceDescriptor.setDescriptorTag(descriptorTag)
        serviceDescriptor.setDescriptorLength(descriptorLength)
        serviceDescriptor.setServiceType(descriptorBuff[0])
        val serviceProviderNameLength = descriptorBuff[1].toInt()
        serviceDescriptor.setServiceProviderNameLength(descriptorBuff[1].toInt())
        val serviceProviderNameContent = ByteArray(serviceProviderNameLength)
        System.arraycopy(
            descriptorBuff,
            2,
            serviceProviderNameContent,
            0,
            serviceProviderNameLength
        )
        val providerName = StringBuilder()
        for (charLocation in serviceProviderNameContent) {
            val providerNameCharLocation: Byte = charLocation and 0xFF.toByte()
            if (providerNameCharLocation >= 0x20 && providerNameCharLocation <= 0x7E) {
                val providerNameChar: Char =
                    CharacterTable.CharacterCodeTable.get(providerNameCharLocation.toInt())
                providerName.append(providerNameChar)
            }
        }
        serviceDescriptor.setServiceProviderName(providerName.toString())
        var serviceNameStart = 2 + serviceProviderNameLength
        val serviceNameLength = descriptorBuff[serviceNameStart].toInt()
        serviceDescriptor.setServiceNameLength(serviceNameLength)
        val serviceNameContent = ByteArray(serviceNameLength)
        serviceNameStart++
        System.arraycopy(descriptorBuff, serviceNameStart, serviceNameContent, 0, serviceNameLength)
        val serviceName = StringBuilder()
        for (charLocation in serviceNameContent) {
            val serviceNameCharLocation: Byte = charLocation and 0xFF.toByte()
            if (serviceNameCharLocation >= 0x20 && serviceNameCharLocation <= 0x7E) {
                val providerNameChar: Char =
                    CharacterTable.CharacterCodeTable.get(serviceNameCharLocation.toInt())
                serviceName.append(providerNameChar)
            }
            serviceDescriptor.setServiceName(serviceName.toString())
        }
        return serviceDescriptor
    }

    companion object {
        private const val SERVICE_DESCRIPTOR_TAG = 0X48
        private const val DESCRIPTOR_HEAD_LENGTH = 2

        fun composeDescriptor(descriptorContent: ByteArray?): ArrayList<Descriptor>? {
            if (descriptorContent == null || descriptorContent.isEmpty()) {
                return null
            }

            val descriptorList = ArrayList<Descriptor>()
            var i = 0
            while (i < descriptorContent.size - 1) {
                val descriptor = Descriptor()
                descriptor.setDescriptorTag(descriptorContent[i].toInt())
                val descriptorLength: Int = descriptorContent[i + 1].toInt() and 0xFF

                descriptor.setDescriptorLength(descriptorLength)
                val descriptorBuff = ByteArray(descriptorLength)
                if ((i + descriptorLength + DESCRIPTOR_HEAD_LENGTH) > descriptorContent.size - 1) {
                    break
                }
                System.arraycopy(
                    descriptorContent,
                    i + DESCRIPTOR_HEAD_LENGTH,
                    descriptorBuff,
                    0,
                    descriptorLength
                )
                descriptor.setDescriptorBuff(descriptorBuff)
                descriptorList.add(descriptor)
                i += DESCRIPTOR_HEAD_LENGTH + descriptorLength
            }
            return descriptorList
        }
    }
}

package com.example.libtosmartparse.table

import com.example.libtosmartparse.descriptor.Descriptor
import com.example.libtosmartparse.descriptor.DescriptorManager
import com.example.libtosmartparse.packet.or
import com.example.libtosmartparse.packet.shl
import com.example.libtosmartparse.packet.shr
import com.example.libtosmartparse.section.ServiceDescriptionSection
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
class ServiceDescriptionTableManager {
    private val mSections: HashMap<Int, ServiceDescriptionSection> =
        HashMap<Int, ServiceDescriptionSection>()
    private var mVersionNumber = -1
    fun composeSection(serviceDescriptionSection: ServiceDescriptionSection?): Boolean {
        if (serviceDescriptionSection == null) {
            return false
        }
        val lastSectionNumber: Int = serviceDescriptionSection.getLastSectionNumber()
        if (mVersionNumber != -1) {
            if (mVersionNumber != serviceDescriptionSection.getVersionNumber()) {
                mVersionNumber = -1
                mSections.clear()
                return false
            }
            if (mSections.containsKey(serviceDescriptionSection.getVersionNumber())) {
                return false
            }
        }
        mSections[serviceDescriptionSection.getSectionNumber()] = serviceDescriptionSection
        mVersionNumber = serviceDescriptionSection.getVersionNumber()
        return mSections.size == lastSectionNumber + 1
    }

    fun getServiceDescriptionTable(): ServiceDescriptionTable? {
        if (mSections.size <= 0) {
            return null
        }
        val serviceDescriptionTable = ServiceDescriptionTable()
        for (i in 0 until mSections.size) {
            val serviceDescriptionSection: ServiceDescriptionSection? = mSections[i]
            val serviceData: ByteArray? =
                if (serviceDescriptionSection != null) serviceDescriptionSection.getData() else null
            if (serviceData != null) {
                val services = composeServices(serviceData)
                serviceDescriptionTable.setService(services)
            }
        }
        serviceDescriptionTable.setVersionNumber(mVersionNumber)
        return serviceDescriptionTable
    }

    private fun composeServices(serviceData: ByteArray): ArrayList<Service>? {
        if (serviceData.size <= 0) {
            return null
        }
        val serviceList = ArrayList<Service>()
        var i = 0
        while (i < serviceData.size) {
            val service = Service()
            //programData[j] shl 8 or programData[j + 1].toInt() and 0xFF
            val serviceId: Int = serviceData[i]  shl 8 or serviceData[i + 1].toInt() and 0xFF
            service.setServiceId(serviceId)
            val scheduleFlag: Int = serviceData[i + 2] shr 1 and 0x01
            service.setEITScheduleFlag(scheduleFlag)
            val presentFollowingFlag: Byte = serviceData[i + 2] and 0x01
            service.setEITPresentFollowingFlag(presentFollowingFlag.toInt())
            val runningStatus: Int = serviceData[i + 3] and 0xE0.toByte() shr 5
            service.setRunningStatus(runningStatus)
            val freeMode: Int = serviceData[i + 3] and 0x10 shr 4
            service.setFreeCAMode(freeMode)
            val descriptorsLoopLength: Int = serviceData[i + 3] and 0x0F shl 8 or serviceData[i + 4]
            service.setDescriptorsLoopLength(descriptorsLoopLength)
            if (descriptorsLoopLength > 0) {
                val descriptorData = ByteArray(descriptorsLoopLength)
                val serviceDescriptorStart = i + SERVICE_HEAD_LENGTH
                System.arraycopy(
                    serviceData,
                    serviceDescriptorStart,
                    descriptorData,
                    0,
                    descriptorsLoopLength
                )
                val descriptors: ArrayList<Descriptor> =
                    DescriptorManager.composeDescriptor(descriptorData)!!
                service.setDescriptor(descriptors)
            }
            serviceList.add(service)
            i = i + SERVICE_HEAD_LENGTH + descriptorsLoopLength
        }
        return serviceList
    }

    companion object {
        private const val SERVICE_HEAD_LENGTH = 5
    }
}

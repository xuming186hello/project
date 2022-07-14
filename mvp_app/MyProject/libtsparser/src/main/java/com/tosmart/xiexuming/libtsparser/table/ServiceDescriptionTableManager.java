package com.tosmart.xiexuming.libtsparser.table;


import com.tosmart.xiexuming.libtsparser.descriptor.Descriptor;
import com.tosmart.xiexuming.libtsparser.descriptor.DescriptorManager;
import com.tosmart.xiexuming.libtsparser.section.ServiceDescriptionSection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xiexuming
 */
public class ServiceDescriptionTableManager {
    private static final int SERVICE_HEAD_LENGTH = 5;

    private final HashMap<Integer, ServiceDescriptionSection> mSections = new HashMap<>();
    private int mVersionNumber = -1;

    public boolean composeSection(ServiceDescriptionSection serviceDescriptionSection) {
        if (serviceDescriptionSection == null) {
            return false;
        }
        int lastSectionNumber = serviceDescriptionSection.getLastSectionNumber();
        if (mVersionNumber != -1) {
            if (mVersionNumber != serviceDescriptionSection.getVersionNumber()) {
                mVersionNumber = -1;
                mSections.clear();
                return false;
            }
            if (mSections.containsKey(serviceDescriptionSection.getVersionNumber())) {
                return false;
            }
        }
        mSections.put(serviceDescriptionSection.getSectionNumber(), serviceDescriptionSection);
        mVersionNumber = serviceDescriptionSection.getVersionNumber();
        return mSections.size() == (lastSectionNumber + 1);
    }

    public ServiceDescriptionTable getServiceDescriptionTable() {
        if (mSections.size() <= 0) {
            return null;
        }
        ServiceDescriptionTable serviceDescriptionTable = new ServiceDescriptionTable();
        for (int i = 0; i < mSections.size(); i++) {
            ServiceDescriptionSection serviceDescriptionSection = mSections.get(i);
            byte[] serviceData = serviceDescriptionSection != null ? serviceDescriptionSection.getData() : null;
            if (serviceData != null) {
                ArrayList<Service> services = composeServices(serviceData);
                serviceDescriptionTable.setService(services);
            }
        }
        serviceDescriptionTable.setVersionNumber(mVersionNumber);
        return serviceDescriptionTable;
    }

    private ArrayList<Service> composeServices(byte[] serviceData) {
        if (serviceData.length <= 0) {
            return null;
        }
        ArrayList<Service> serviceList = new ArrayList<>();
        for (int i = 0; i < serviceData.length; ) {
            Service service = new Service();
            int serviceId = (serviceData[i] & 0xFF) << 8 | (serviceData[i + 1] & 0xFF);
            service.setServiceId(serviceId);
            int scheduleFlag = serviceData[i + 2] >> 1 & 0x01;
            service.setEITScheduleFlag(scheduleFlag);
            int presentFollowingFlag = serviceData[i + 2] & 0x01;
            service.setEITPresentFollowingFlag(presentFollowingFlag);
            int runningStatus = (serviceData[i + 3] & 0xE0) >> 5;
            service.setRunningStatus(runningStatus);
            int freeMode = (serviceData[i + 3] & 0x10) >> 4;
            service.setFreeCAMode(freeMode);
            int descriptorsLoopLength = (serviceData[i + 3] & 0x0F) << 8 | serviceData[i + 4];
            service.setDescriptorsLoopLength(descriptorsLoopLength);

            if (descriptorsLoopLength > 0) {
                byte[] descriptorData = new byte[descriptorsLoopLength];
                int serviceDescriptorStart = i + SERVICE_HEAD_LENGTH;
                System.arraycopy(serviceData, serviceDescriptorStart, descriptorData, 0, descriptorsLoopLength);
                ArrayList<Descriptor> descriptors = DescriptorManager.composeDescriptor(descriptorData);
                service.setDescriptor(descriptors);
            }

            serviceList.add(service);
            i = i + SERVICE_HEAD_LENGTH + descriptorsLoopLength;
        }
        return serviceList;
    }
}

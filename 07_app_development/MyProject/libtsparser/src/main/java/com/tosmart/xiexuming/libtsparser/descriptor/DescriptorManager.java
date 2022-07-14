package com.tosmart.xiexuming.libtsparser.descriptor;

import com.tosmart.xiexuming.libtsparser.table.CharacterTable;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class DescriptorManager implements DescriptorExtension {
    private static final int SERVICE_DESCRIPTOR_TAG = 0X48;
    private static final int DESCRIPTOR_HEAD_LENGTH = 2;

    public static ArrayList<Descriptor> composeDescriptor(byte[] descriptorContent) {
        if (descriptorContent.length <= 0) {
            return null;
        }
        ArrayList<Descriptor> descriptorList = new ArrayList<>();
        for (int i = 0; i < descriptorContent.length; ) {
            Descriptor descriptor = new Descriptor();
            descriptor.setDescriptorTag(descriptorContent[i]);
            int descriptorLength = descriptorContent[i + 1] & 0xFF;
            descriptor.setDescriptorLength(descriptorLength);
            byte[] descriptorBuff = new byte[descriptorLength];
            System.arraycopy(descriptorContent, i + DESCRIPTOR_HEAD_LENGTH, descriptorBuff, 0, descriptorLength);
            descriptor.setDescriptorBuff(descriptorBuff);
            descriptorList.add(descriptor);
            i = i + DESCRIPTOR_HEAD_LENGTH + descriptorLength;
        }
        return descriptorList;
    }

    @Override
    public ServiceDescriptor getServiceDescriptor(Descriptor descriptor) {
        if (descriptor == null) {
            return null;
        }

        int descriptorTag = descriptor.getDescriptorTag();
        if (descriptorTag != SERVICE_DESCRIPTOR_TAG) {
            return null;
        }
        int descriptorLength = descriptor.getDescriptorLength();
        if (descriptorLength <= 0) {
            return null;
        }
        byte[] descriptorBuff = descriptor.getDescriptorBuff();
        if (descriptorBuff == null) {
            return null;
        }

        ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
        serviceDescriptor.setDescriptorTag(descriptorTag);
        serviceDescriptor.setDescriptorLength(descriptorLength);
        serviceDescriptor.setServiceType(descriptorBuff[0]);
        int serviceProviderNameLength = descriptorBuff[1];

        serviceDescriptor.setServiceProviderNameLength(descriptorBuff[1]);
        byte[] serviceProviderNameContent = new byte[serviceProviderNameLength];
        System.arraycopy(descriptorBuff, 2, serviceProviderNameContent, 0, serviceProviderNameLength);
        StringBuilder providerName = new StringBuilder();
        for (byte charLocation : serviceProviderNameContent) {
            int providerNameCharLocation = charLocation & 0xFF;
            if (providerNameCharLocation >= 0x20 && providerNameCharLocation <= 0x7E) {
                char providerNameChar = CharacterTable.CharacterCodeTable[providerNameCharLocation];
                providerName.append(providerNameChar);
            }
        }

        serviceDescriptor.setServiceProviderName(providerName.toString());
        int serviceNameStart = 2 + serviceProviderNameLength;
        int serviceNameLength = descriptorBuff[serviceNameStart];
        serviceDescriptor.setServiceNameLength(serviceNameLength);
        byte[] serviceNameContent = new byte[serviceNameLength];
        serviceNameStart++;
        System.arraycopy(descriptorBuff, serviceNameStart, serviceNameContent, 0, serviceNameLength);
        StringBuilder serviceName = new StringBuilder();
        for (byte charLocation : serviceNameContent) {
            int serviceNameCharLocation = charLocation & 0xFF;
            if (serviceNameCharLocation >= 0x20 && serviceNameCharLocation <= 0x7E) {
                char providerNameChar = CharacterTable.CharacterCodeTable[serviceNameCharLocation];
                serviceName.append(providerNameChar);

            }
            serviceDescriptor.setServiceName(serviceName.toString());
        }
        return serviceDescriptor;
    }
}

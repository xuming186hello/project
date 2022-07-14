package com.tosmart.xiexuming.libtsparser.descriptor;

import androidx.annotation.NonNull;


/**
 * @author xiexuming
 */
public class ServiceDescriptor extends Descriptor {
    private int serviceType;
    private int serviceProviderNameLength;
    private String serviceProviderName;
    private int serviceNameLength;
    private String serviceName;

    @NonNull
    @Override
    public String toString() {
        return "ServiceDescriptor{" +
                "descriptorTag=" + descriptorTag +
                ", descriptorLength=" + descriptorLength +
                ", serviceType=" + serviceType +
                ", serviceProviderNameLength=" + serviceProviderNameLength +
                ", serviceProviderName=" + serviceProviderName +
                ", serviceNameLength=" + serviceNameLength +
                ", serviceName=" + serviceName +
                '}';
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceProviderNameLength() {
        return serviceProviderNameLength;
    }

    public void setServiceProviderNameLength(int serviceProviderNameLength) {
        this.serviceProviderNameLength = serviceProviderNameLength;
    }

    public int getServiceNameLength() {
        return serviceNameLength;
    }

    public void setServiceNameLength(int serviceNameLength) {
        this.serviceNameLength = serviceNameLength;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

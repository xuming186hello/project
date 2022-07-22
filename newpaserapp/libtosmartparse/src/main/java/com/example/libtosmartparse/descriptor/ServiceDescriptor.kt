package com.example.libtosmartparse.descriptor


/**
 * @author xxm
 * 2022/7/17
 **/
class ServiceDescriptor : Descriptor() {
    private var serviceType = 0
    private var serviceProviderNameLength = 0
    private var serviceProviderName: String? = null
    private var serviceNameLength = 0
    private var serviceName: String? = null

    override fun toString(): String {
        return "ServiceDescriptor{" +
                "descriptorTag=" + getDescriptorTag() +
                ", descriptorLength=" + getDescriptorLength() +
                ", serviceType=" + serviceType +
                ", serviceProviderNameLength=" + serviceProviderNameLength +
                ", serviceProviderName=" + serviceProviderName +
                ", serviceNameLength=" + serviceNameLength +
                ", serviceName=" + serviceName +
                '}'
    }

    fun getServiceType(): Int {
        return serviceType
    }

    fun setServiceType(serviceType: Byte) {
        this.serviceType = serviceType.toInt()
    }

    fun getServiceProviderNameLength(): Int {
        return serviceProviderNameLength
    }

    fun setServiceProviderNameLength(serviceProviderNameLength: Int) {
        this.serviceProviderNameLength = serviceProviderNameLength
    }

    fun getServiceNameLength(): Int {
        return serviceNameLength
    }

    fun setServiceNameLength(serviceNameLength: Int) {
        this.serviceNameLength = serviceNameLength
    }

    fun getServiceProviderName(): String? {
        return serviceProviderName
    }

    fun setServiceProviderName(serviceProviderName: String?) {
        this.serviceProviderName = serviceProviderName
    }

    fun getServiceName(): String? {
        return serviceName
    }

    fun setServiceName(serviceName: String?) {
        this.serviceName = serviceName
    }
}

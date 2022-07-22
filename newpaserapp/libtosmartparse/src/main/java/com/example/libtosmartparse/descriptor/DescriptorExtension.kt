package com.example.libtosmartparse.descriptor

/**
 * @author xxm
 * 2022/7/17
 **/
open interface DescriptorExtension {
    /**
     * 解析成serviceDescriptor
     *
     * @param descriptor 普通descriptor
     * @return 返回serviceDescriptor
     */
    fun getServiceDescriptor(descriptor: Descriptor?): ServiceDescriptor?
}
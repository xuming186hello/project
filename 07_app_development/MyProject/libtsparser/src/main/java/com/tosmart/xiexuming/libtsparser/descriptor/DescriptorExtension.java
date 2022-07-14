package com.tosmart.xiexuming.libtsparser.descriptor;

/**
 * @author xiexuming
 */
public interface DescriptorExtension {
    /**
     * 解析成serviceDescriptor
     *
     * @param descriptor 普通descriptor
     * @return 返回serviceDescriptor
     */
    ServiceDescriptor getServiceDescriptor(Descriptor descriptor);
}

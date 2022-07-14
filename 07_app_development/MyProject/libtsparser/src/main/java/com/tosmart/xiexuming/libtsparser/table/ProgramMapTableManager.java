package com.tosmart.xiexuming.libtsparser.table;

import com.tosmart.xiexuming.libtsparser.descriptor.Descriptor;
import com.tosmart.xiexuming.libtsparser.descriptor.DescriptorManager;
import com.tosmart.xiexuming.libtsparser.section.ProgramMapSection;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class ProgramMapTableManager {
    private static final int ELEMENT_STREAM_HEAD_LENGTH = 5;

    public ProgramMapTable getProgramMapTable(ProgramMapSection programMapSection, int pid) {
        ProgramMapTable programMapTable = new ProgramMapTable();
        programMapTable.setPid(pid);
        byte[] programDescriptorContent = programMapSection.getDescriptor();
        ArrayList<Descriptor> programDescriptor = DescriptorManager.composeDescriptor(programDescriptorContent);
        programMapTable.setProgramInfo(programDescriptor);

        byte[] elementStreamContent = programMapSection.getData();
        ArrayList<Component> components = composeComponents(elementStreamContent);

        programMapTable.setComponents(components);
        return programMapTable;
    }

    public ArrayList<Component> composeComponents(byte[] elementStreamContent) {
        if (elementStreamContent.length <= 0) {
            return null;
        }

        ArrayList<Component> componentList = new ArrayList<>();
        for (int i = 0; i < elementStreamContent.length; ) {
            Component component = new Component();
            int streamType = elementStreamContent[i];
            component.setElementaryPid(streamType);
            int elementaryPid = (elementStreamContent[i + 1] & 0x1F) << 8 | elementStreamContent[i + 2];
            component.setElementaryPid(elementaryPid);
            int elementStreamInfoLength = (elementStreamContent[i + 3] & 0x0F) | elementStreamContent[i + 4];
            component.setElementStreamInfoLength(elementStreamInfoLength);
            byte[] esDescriptorContent = new byte[elementStreamInfoLength];
            int elementStreamContentStart = i + ELEMENT_STREAM_HEAD_LENGTH;
            System.arraycopy(elementStreamContent, elementStreamContentStart, esDescriptorContent, 0, elementStreamInfoLength);
            ArrayList<Descriptor> esDescriptor = DescriptorManager.composeDescriptor(esDescriptorContent);
            component.setDescriptor(esDescriptor);
            componentList.add(component);
            i = i + elementStreamInfoLength + ELEMENT_STREAM_HEAD_LENGTH;
        }
        return componentList;
    }
}

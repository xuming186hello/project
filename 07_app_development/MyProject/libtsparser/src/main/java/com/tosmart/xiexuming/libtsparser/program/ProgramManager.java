package com.tosmart.xiexuming.libtsparser.program;

import com.tosmart.xiexuming.libtsparser.descriptor.Descriptor;
import com.tosmart.xiexuming.libtsparser.descriptor.DescriptorExtension;
import com.tosmart.xiexuming.libtsparser.descriptor.DescriptorManager;
import com.tosmart.xiexuming.libtsparser.descriptor.ServiceDescriptor;
import com.tosmart.xiexuming.libtsparser.table.ProgramAssociationTable;
import com.tosmart.xiexuming.libtsparser.table.ProgramMapTable;
import com.tosmart.xiexuming.libtsparser.table.Service;
import com.tosmart.xiexuming.libtsparser.table.ServiceDescriptionTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xiexuming
 */
public class ProgramManager {
    private static volatile ProgramManager instance;

    private ProgramManager() {
    }

    public static ProgramManager getInstance() {
        if (instance == null) {
            synchronized (ProgramManager.class) {
                instance = new ProgramManager();
            }
        }
        return instance;
    }

    public List<Program> getProgramList(ProgramAssociationTable programAssociationTable,
                                        ServiceDescriptionTable serviceDescriptionTable,
                                        HashMap<Integer, ProgramMapTable> programMapTableMap) {
        if (programAssociationTable == null && serviceDescriptionTable == null) {
            return null;
        }
        ArrayList<Program> programs = programAssociationTable != null ? programAssociationTable.getPrograms() : null;
        ArrayList<Service> services = serviceDescriptionTable.getService();
        if (programs == null) {
            return null;
        }
        for (Program program : programs) {
            int programNumber = program.getProgramNumber();
            for (Service service : services) {
                if (programNumber != service.getServiceId()) {
                    continue;
                }
                ArrayList<Descriptor> descriptorList = service.getDescriptor();
                String serviceName = getServiceName(descriptorList);
                program.setProgramName(serviceName);
                break;
            }
            int programMapPid = program.getProgramMapPid();
            if (programMapTableMap.containsKey(programMapPid)) {
                ProgramMapTable programMapTable = programMapTableMap.get(programMapPid);
                program.setProgramMapTable(programMapTable);
            }
        }
        System.out.println(programs);
        return programs;
    }

    private String getServiceName(ArrayList<Descriptor> descriptorList) {
        DescriptorExtension descriptorExtension = new DescriptorManager();
        for (Descriptor descriptor : descriptorList) {
            ServiceDescriptor serviceDescriptor = descriptorExtension.getServiceDescriptor(descriptor);
            if (descriptor == null) {
                continue;
            }
            return serviceDescriptor.getServiceName();
        }
        return "";
    }
}

import com.example.libtosmartparse.descriptor.DescriptorExtension
import com.example.libtosmartparse.descriptor.ServiceDescriptor
import com.example.libtosmartparse.table.ProgramAssociationTable
import com.example.libtosmartparse.table.Service
import com.example.libtosmartparse.table.ServiceDescriptionTable
import com.example.libtosmartparse.descriptor.Descriptor
import com.example.libtosmartparse.descriptor.DescriptorManager

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramManager private constructor() {
    fun getProgramList(
        programAssociationTable: ProgramAssociationTable?,
        serviceDescriptionTable: ServiceDescriptionTable?,
        programMapTableMap: HashMap<Int, ProgramMapTable>?
    ): List<Program>? {
        if (programAssociationTable == null && serviceDescriptionTable == null) {
            return null
        }

        val programs: ArrayList<Program>? =
            programAssociationTable?.getPrograms()
        val services: ArrayList<Service>? = serviceDescriptionTable?.getService()
        if (programs == null) {
            return null
        }
        for (program in programs) {
            val programNumber: Int = program.getProgramNumber()
            if (services != null) {
                for (service in services) {
                    if (programNumber != service.getServiceId()) {
                        continue
                    }
                    val descriptorList: ArrayList<Descriptor> = service.getDescriptor()!!
                    val serviceName = getServiceName(descriptorList)
                    program.setProgramName(serviceName)
                    break
                }
            }
            val programMapPid: Int = program.getProgramMapPid()
            if (programMapTableMap!!.containsKey(programMapPid)) {
                val programMapTable: ProgramMapTable? = programMapTableMap[programMapPid]
                program.setProgramMapTable(programMapTable)
            }
        }
        println(programs)
        return programs
    }

    private fun getServiceName(descriptorList: ArrayList<Descriptor>): String? {
        val descriptorExtension: DescriptorExtension = DescriptorManager()
        for (descriptor in descriptorList) {
            val serviceDescriptor: ServiceDescriptor =
                descriptorExtension.getServiceDescriptor(descriptor)!!
            return serviceDescriptor.getServiceName()
        }
        return "~"
    }

    companion object {
        @Volatile
        var instance: ProgramManager? = null
            get() {
                if (field == null) {
                    synchronized(this) { field = ProgramManager() }
                }
                return field
            }
            private set
    }
}

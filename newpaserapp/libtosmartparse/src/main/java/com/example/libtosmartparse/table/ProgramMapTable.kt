
import com.example.libtosmartparse.table.Component
import com.example.libtosmartparse.descriptor.Descriptor

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramMapTable {
    private var components: ArrayList<Component>? = null
    private var pid = 0
    private var programInfo: ArrayList<Descriptor>? = null

    override fun toString(): String {
        return """
            ProgramMapTable{pid=0x${Integer.toHexString(pid)}, programInfo=$programInfo，
             components=$components} 
            """.trimIndent()
    }

    fun getComponents(): ArrayList<Component>? {
        return components
    }

    fun setComponents(components: ArrayList<Component>?) {
        this.components = components
    }

    fun getPid(): Int {
        return pid
    }

    fun setPid(pid: Int) {
        this.pid = pid
    }

    fun getProgramInfo(): ArrayList<Descriptor>? {
        return programInfo
    }

    fun setProgramInfo(programInfo: ArrayList<Descriptor>?) {
        this.programInfo = programInfo
    }
}

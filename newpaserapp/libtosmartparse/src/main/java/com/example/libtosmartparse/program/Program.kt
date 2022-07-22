/**
 * @author xxm
 * 2022/7/17
 **/
class Program {
    private var programNumber = 0
    private var programMapPid = 0
    private var networkId = 0
    private var programName: String? = null
    private var programMapTable: ProgramMapTable? = null

    override fun toString(): String {
        return """
             Program{programNumber=0x${Integer.toHexString(programNumber)}, programMapPid=0x${
            Integer.toHexString(
                programMapPid
            )
        }, networkId=0x${Integer.toHexString(networkId)}, programName='$programName' 
             $programMapTable}
             
             """.trimIndent()
    }

    fun getProgramMapTable(): ProgramMapTable? {
        return programMapTable
    }

    fun setProgramMapTable(programMapTable: ProgramMapTable?) {
        this.programMapTable = programMapTable
    }

    fun getNetworkId(): Int {
        return networkId
    }

    fun setNetworkId(networkId: Int) {
        this.networkId = networkId
    }

    fun getProgramName(): String? {
        return programName
    }

    fun setProgramName(programName: String?) {
        this.programName = programName
    }

    fun getProgramNumber(): Int {
        return programNumber
    }

    fun setProgramNumber(programNumber: Int) {
        this.programNumber = programNumber
    }

    fun getProgramMapPid(): Int {
        return programMapPid
    }

    fun setProgramMapPid(programMapPid: Int) {
        this.programMapPid = programMapPid
    }
}

import com.example.libtosmartparse.packet.TsPacketManager
import com.example.libtosmartparse.section.*
import com.example.libtosmartparse.table.*
import java.util.*

/**
 * @author xxm
 * 2022/7/18
 **/
class TransportStreamManager {
    var mProgramAssociationSectionManager: ProgramAssociationSectionManager
    var mProgramMapSectionManager: ProgramMapSectionManager
    var mServiceDescriptionSectionManager: ServiceDescriptionSectionManager
    var mProgramAssociationTableManager: ProgramAssociationTableManager
    var mProgramMapTableManager: ProgramMapTableManager
    var mServiceDescriptionTableManager: ServiceDescriptionTableManager
    var mProgramMapTableMap: HashMap<Int, ProgramMapTable>? = null
    var mServiceDescriptionTable: ServiceDescriptionTable? = null
    var mProgramAssociationTable: ProgramAssociationTable? = null
    var mPacketManager: TsPacketManager

    fun parseTsStream(filePath: String?): List<Program>? {
        try {
            val tsPacketLength: Int =
                if (filePath.isNullOrBlank()) 0 else mPacketManager.getTsPacketLength(filePath)
            if (tsPacketLength <= 0) {
                return null
            }
            val sectionListener: SectionListener = object : SectionListener {
                override fun makePat(programAssociationSection: ProgramAssociationSection?) {
                    val programAssociationComposeFinish: Boolean =
                        mProgramAssociationTableManager.composeSection(programAssociationSection)
                    if (programAssociationComposeFinish) {
                        mProgramAssociationTable =
                            mProgramAssociationTableManager.getProgramAssociationTable()
                        val programPidList: ArrayList<Int> =
                            mProgramAssociationTableManager.getProgramPidList()
                        mProgramMapSectionManager.setFilterPidList(programPidList)
                        mPacketManager.deleteObserver(mProgramAssociationSectionManager)
                    }
                }

                override fun makePmt(pid: Int, programMapSection: ProgramMapSection?) {
                    val programMapTable: ProgramMapTable =
                        mProgramMapTableManager.getProgramMapTable(programMapSection!!, pid)
                    mProgramMapTableMap?.set(pid, programMapTable)
                    val filterPidList: ArrayList<Int> = mProgramMapSectionManager.getFilterPidList()
                    if (filterPidList.size <= 0) {
                        mPacketManager.deleteObserver(mProgramMapSectionManager)
                    }
                }

                override fun makeSdt(serviceDescriptionSection: ServiceDescriptionSection?) {
                    val serviceDescriptionComposeFinish: Boolean =
                        mServiceDescriptionTableManager.composeSection(serviceDescriptionSection)
                    if (serviceDescriptionComposeFinish) {
                        mServiceDescriptionTable =
                            mServiceDescriptionTableManager.getServiceDescriptionTable()
                        mPacketManager.deleteObserver(mServiceDescriptionSectionManager)
                    }
                }
            }

            mProgramAssociationSectionManager.setListener(sectionListener)
            mProgramMapSectionManager.setListener(sectionListener)
            mServiceDescriptionSectionManager.setListener(sectionListener)
            mPacketManager.addObserver(mProgramMapSectionManager)
            mPacketManager.addObserver(mProgramAssociationSectionManager)
            mPacketManager.addObserver(mServiceDescriptionSectionManager)
            mPacketManager.distributePacket(filePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ProgramManager.instance
            ?.getProgramList(
                mProgramAssociationTable,
                mServiceDescriptionTable, mProgramMapTableMap
            )
    }

    fun forceClose() {
        mPacketManager.deleteObservers()
    }

    init {
        mPacketManager = TsPacketManager.getInstance() as TsPacketManager
        mProgramMapTableMap = HashMap<Int, ProgramMapTable>()
        mServiceDescriptionTableManager = ServiceDescriptionTableManager()
        mProgramAssociationSectionManager = ProgramAssociationSectionManager()
        mProgramMapSectionManager = ProgramMapSectionManager()
        mServiceDescriptionSectionManager = ServiceDescriptionSectionManager()
        mProgramAssociationTableManager = ProgramAssociationTableManager()
        mProgramMapTableManager = ProgramMapTableManager()
    }
}

package com.example.libtosmartparse.table

import Program
import com.example.libtosmartparse.packet.shl

import com.example.libtosmartparse.section.ProgramAssociationSection
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramAssociationTableManager {
    private val mProgramPidList = ArrayList<Int>()
    private val mSections: HashMap<Int, ProgramAssociationSection> =
        HashMap()
    private var mVersionNumber = -1
    fun composeSection(programAssociationSection: ProgramAssociationSection?): Boolean {
        if (programAssociationSection == null) {
            return false
        }
        val lastSectionNumber: Int = programAssociationSection.getLastSectionNumber()
        if (mVersionNumber != -1) {
            if (mVersionNumber != programAssociationSection.getVersionNumber()) {
                mVersionNumber = -1
                mSections.clear()
                return false
            }
            if (mSections.containsKey(programAssociationSection.getVersionNumber())) {
                return false
            }
        }
        mSections[programAssociationSection.getSectionNumber()] = programAssociationSection
        mVersionNumber = programAssociationSection.getVersionNumber()
        return mSections.size == lastSectionNumber + 1
    }

    fun getProgramAssociationTable(): ProgramAssociationTable {
        val mProgramAssociationTable = ProgramAssociationTable()
        val programList: ArrayList<Program> = ArrayList<Program>()
        for (i in 0 until mSections.size) {
            val programAssociationSection: ProgramAssociationSection? = mSections[i]
            val programData: ByteArray =
                (programAssociationSection?.getData())
                    ?: continue
            val programs: ArrayList<Program> = composePrograms(programData) ?: continue
            programList.addAll(programs)
        }

        mProgramAssociationTable.setPrograms(programList)
        mProgramAssociationTable.setVersion(mVersionNumber)
        return mProgramAssociationTable
    }

    private fun composePrograms(programData: ByteArray): ArrayList<Program>? {
        if (programData.size <= 0) {
            return null
        }
        val programList: ArrayList<Program> = ArrayList<Program>()
        var j = 0
        while (j < programData.size) {
            val program = Program()
            val programNumber: Int = programData[j] shl 8 or programData[j + 1].toInt() and 0xFF
            program.setProgramNumber(programNumber)
            val programMapPid: Int =
                programData[j + PROGRAM_NUMBER_LENGTH] and 0x1F shl 8 or
                        ((programData[j + PROGRAM_NUMBER_LENGTH + 1] and 0xFF.toByte()).toInt())
            if (programNumber == 0) {
                program.setNetworkId(programMapPid)
                program.setProgramMapPid(FILL_CHARACTER)
            } else {
                program.setProgramMapPid(programMapPid)
                program.setNetworkId(FILL_CHARACTER)
                mProgramPidList.add(programMapPid)
            }
            programList.add(program)
            j = j + PROGRAM_LENGTH
        }
        return programList
    }

    fun getProgramPidList(): ArrayList<Int> {
        return mProgramPidList
    }

    companion object {
        private const val PROGRAM_NUMBER_LENGTH = 2
        private const val PROGRAM_LENGTH = 4
        private const val FILL_CHARACTER = -0x1
    }
}

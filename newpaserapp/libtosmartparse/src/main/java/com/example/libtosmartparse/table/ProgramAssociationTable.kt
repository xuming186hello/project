package com.example.libtosmartparse.table

import Program

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramAssociationTable {
    private var programs: ArrayList<Program>? = null
    private var version = 0
    override fun toString(): String {
        return "ProgramAssociationTable{" +
                " version=" + version +
                ", programs=" + programs +
                '}'
    }

    fun getPrograms(): ArrayList<Program>? {
        return programs
    }

    fun setPrograms(programs: ArrayList<Program>?) {
        this.programs = programs
    }

    fun getVersion(): Int {
        return version
    }

    fun setVersion(version: Int) {
        this.version = version
    }
}

package com.example.libtosmartparse.section

/**
 * @author xxm
 * 2022/7/17
 **/
open interface SectionListener {
    /**
     * 组建pat
     *
     * @param programAssociationSection pas
     */
    fun makePat(programAssociationSection: ProgramAssociationSection?)

    /**
     * 组建pat
     * @param pid pid
     * @param programMapSection 特定section
     */
    fun makePmt(pid: Int, programMapSection: ProgramMapSection?)

    /**
     * 组建sdt
     * @param serviceDescriptionSection 特定section
     */
    fun makeSdt(serviceDescriptionSection: ServiceDescriptionSection?)
}
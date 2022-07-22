package com.example.libtosmartparse.section

import kotlin.math.abs

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramMapSection : Section() {
    private var pid = 0
    private var programNumber = 0
    private var versionNumber = 0
    private var currentNextIndicator = 0
    private var sectionNumber = 0
    private var lastSectionNumber = 0
    private var PCR_PID = 0
    private var programInfoLength = 0
    private lateinit var descriptor: ByteArray
    private lateinit var crcCode: ByteArray

    override fun toString(): String {
        return "ProgramMapSection{" +
                "Pid=" + pid +
                ", tableId=" + getTableId() +
                ", sectionLength=" + getSectionLength() +
                ", programNumber=" + programNumber +
                ", versionNumber=" + versionNumber +
                ", currentNextIndicator=" + currentNextIndicator +
                ", sectionNumber=" + sectionNumber +
                ", lastSectionNumber=" + lastSectionNumber +
                ", PCR_PID=" + PCR_PID +
                ", programInfoLength=" + programInfoLength +
                ", descriptor=" +"+ Arrays.toString(descriptor)" +
                ", data=" + "Arrays.toString(getData())" +
                ", crcCode=" + "Arrays.toString(crcCode)" +
                '}'
    }

    fun setHeadDate(programMapSectionHead: IntArray) {
        setTableId(programMapSectionHead[0])
        setReserved(programMapSectionHead[1])
        setSectionLength(programMapSectionHead[2])
        programNumber = programMapSectionHead[3]
        versionNumber = programMapSectionHead[4]
        currentNextIndicator = programMapSectionHead[5]
        sectionNumber = programMapSectionHead[6]
        lastSectionNumber = programMapSectionHead[7]
        PCR_PID = programMapSectionHead[8]
        programInfoLength = abs(programMapSectionHead[9])
    }

    fun getPid(): Int {
        return pid
    }

    fun setPid(pid: Int) {
        this.pid = pid
    }

    fun getProgramNumber(): Int {
        return programNumber
    }

    fun setProgramNumber(programNumber: Int) {
        this.programNumber = programNumber
    }

    fun getVersionNumber(): Int {
        return versionNumber
    }

    fun setVersionNumber(versionNumber: Int) {
        this.versionNumber = versionNumber
    }

    fun getCurrentNextIndicator(): Int {
        return currentNextIndicator
    }

    fun setCurrentNextIndicator(currentNextIndicator: Int) {
        this.currentNextIndicator = currentNextIndicator
    }

    fun getSectionNumber(): Int {
        return sectionNumber
    }

    fun setSectionNumber(sectionNumber: Int) {
        this.sectionNumber = sectionNumber
    }

    fun getLastSectionNumber(): Int {
        return lastSectionNumber
    }

    fun setLastSectionNumber(lastSectionNumber: Int) {
        this.lastSectionNumber = lastSectionNumber
    }

    fun getPCR_PID(): Int {
        return PCR_PID
    }

    fun setPCR_PID(PCR_PID: Int) {
        this.PCR_PID = PCR_PID
    }

    fun getProgramInfoLength(): Int {
        return programInfoLength
    }

    fun setProgramInfoLength(programInfoLength: Int) {
        this.programInfoLength = programInfoLength
    }

    fun getCrcCode(): ByteArray {
        return crcCode
    }

    fun setCrcCode(crcCode: ByteArray) {
        this.crcCode = crcCode
    }

    fun getDescriptor(): ByteArray {
        return descriptor
    }

    fun setDescriptor(descriptor: ByteArray) {
        this.descriptor = descriptor
    }
}

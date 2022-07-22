package com.example.libtosmartparse.section

import java.util.*

/**
 * @author xxm
 * 2022/7/17
 **/
class ProgramAssociationSection : Section() {
    private var transportStreamId = 0
    private var versionNumber = 0
    private var currentNextIndicator = 0
    private var sectionNumber = 0
    private var lastSectionNumber = 0
    private lateinit var crcCode: ByteArray

    fun setHeadDate(programAssociationSectionHead: IntArray) {
        setTableId(programAssociationSectionHead[0])
        setReserved(programAssociationSectionHead[1])
        setSectionLength(programAssociationSectionHead[2])
        transportStreamId = programAssociationSectionHead[3]
        versionNumber = programAssociationSectionHead[4]
        currentNextIndicator = programAssociationSectionHead[5]
        sectionNumber = programAssociationSectionHead[6]
        lastSectionNumber = programAssociationSectionHead[7]
    }

    override fun toString(): String {
        return "ProgramAssociationSection{" +
                "tableId=" + getTableId() +
                ", sectionLength=" + getSectionLength() +
                ", sectionSyntaxIndicator=" + getSectionSyntaxIndicator() +
                ", reserved=" + getReserved() +
                "transportStreamId=" + transportStreamId +
                ", versionNumber=" + versionNumber +
                ", currentNextIndicator=" + currentNextIndicator +
                ", sectionNumber=" + sectionNumber +
                ", lastSectionNumber=" + lastSectionNumber +
                ", crcCode=" + crcCode[0] + " " + crcCode[1] + " " + crcCode[2] + " " + crcCode[3] +
                ", data=" + Arrays.toString(getData()) +
                '}'
    }

    private fun setProgramContent(programContent: ByteArray) {
        setData(programContent)
    }

    fun getTransportStreamId(): Int {
        return transportStreamId
    }

    fun setTransportStreamId(transportStreamId: Int) {
        this.transportStreamId = transportStreamId
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

    fun getCrcCode(): ByteArray {
        return crcCode
    }

    fun setCrcCode(crcCode: ByteArray) {
        this.crcCode = crcCode
    }
}

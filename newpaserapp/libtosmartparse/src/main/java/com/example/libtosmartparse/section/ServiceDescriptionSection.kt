package com.example.libtosmartparse.section

import com.example.libtosmartparse.section.Section
import java.util.*

/**
 * @author xxm
 * 2022/7/17
 **/
class ServiceDescriptionSection : Section() {
    private var transportStreamId = 0
    private var versionNumber = 0
    private var currentNextIndicator = 0
    private var sectionNumber = 0
    private var lastSectionNumber = 0
    private var originalNetworkId = 0
    private lateinit var crcCode: ByteArray

    override fun toString(): String {
        return "ServiceDescriptionSection{" +
                ", tableId=" + getTableId() +
                ", sectionLength=" + getSectionLength() +
                ", currentLength=" + getCurrentLength() +
                ", sectionSyntaxIndicator=" + getSectionSyntaxIndicator() +
                ", reserved=" + getReserved() +
                ", transportStreamId=" + transportStreamId +
                ", versionNumber=" + versionNumber +
                ", currentNextIndicator=" + currentNextIndicator +
                ", sectionNumber=" + sectionNumber +
                ", lastSectionNumber=" + lastSectionNumber +
                ", originalNetworkId=" + originalNetworkId +
                ", data=" + Arrays.toString(getData()) +
                ", crcCode=" + Arrays.toString(crcCode) +
                '}'
    }

    fun setHeadDate(programAssociationSectionHead: IntArray) {
        setTableId(programAssociationSectionHead[0])
        setReserved(programAssociationSectionHead[1])
        setSectionLength(programAssociationSectionHead[2])
        transportStreamId = programAssociationSectionHead[3]
        versionNumber = programAssociationSectionHead[4]
        currentNextIndicator = programAssociationSectionHead[5]
        sectionNumber = programAssociationSectionHead[6]
        lastSectionNumber = programAssociationSectionHead[7]
        originalNetworkId = programAssociationSectionHead[8]
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

    fun getOriginalNetworkId(): Int {
        return originalNetworkId
    }

    fun setOriginalNetworkId(originalNetworkId: Int) {
        this.originalNetworkId = originalNetworkId
    }

    fun getCrcCode(): ByteArray {
        return crcCode
    }

    fun setCrcCode(crcCode: ByteArray) {
        this.crcCode = crcCode
    }
}

package com.example.libtosmartparse.section

import com.example.libtosmartparse.packet.shl
import com.example.libtosmartparse.packet.shr
import java.util.*
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
open class Section {
    private var tableId = 0
    private var sectionLength = 0
    private var currentLength = 0
    private var sectionSyntaxIndicator = 0
    private var reserved = 0
    private lateinit var data: ByteArray

    override fun toString(): String {
        return "Section{" +
                "tableId=" + tableId +
                ", sectionLength=" + sectionLength +
                ", currentLength=" + currentLength +
                ", sectionSyntaxIndicator=" + sectionSyntaxIndicator +
                ", reserved=" + reserved +
                ", data=" + Arrays.toString(data) +
                '}'
    }

    fun setHeadDate(sectionHead: ByteArray) {
        tableId = sectionHead[0].toInt()
        sectionSyntaxIndicator = sectionHead[1] shr 8 and 0x01
        reserved = (sectionHead[1] shr 4) and 0x03
        sectionLength = ((sectionHead[1] and 0x0F) shl 8) or (sectionHead[2].toInt() and 0xFF)

        data = ByteArray(sectionLength)
    }

    fun getSectionSyntaxIndicator(): Int {
        return sectionSyntaxIndicator
    }

    fun setSectionSyntaxIndicator(sectionSyntaxIndicator: Int) {
        this.sectionSyntaxIndicator = sectionSyntaxIndicator
    }

    fun getReserved(): Int {
        return reserved
    }

    fun setReserved(reserved: Int) {
        this.reserved = reserved
    }

    fun getCurrentLength(): Int {
        return currentLength
    }

    fun setCurrentLength(currentLength: Int) {
        this.currentLength = currentLength
    }

    fun getData(): ByteArray? {
        return data
    }

    fun getTableId(): Int {
        return tableId
    }

    fun setTableId(tableId: Int) {
        this.tableId = tableId
    }

    fun getSectionLength(): Int {
        return sectionLength
    }

    fun setSectionLength(sectionLength: Int) {
        this.sectionLength = sectionLength
    }

    open fun setData(data: ByteArray?) {
        this.data = data!!
    }
}
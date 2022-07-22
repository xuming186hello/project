package com.example.libtosmartparse.packet

import java.io.FileInputStream
import java.util.*

/**
 * @author xxm
 * 2022/7/17
 **/
class TsPacketManager private constructor() : Observable() {
    private var mPacketStatLocation: Long = 0
    private var mPacketLong: Int = 0

    fun getTsPacketLength(tsFilePath: String): Int {
        println(tsFilePath)
        mPacketLong = -1
        var firstArray = ByteArray(READ_BYTES)
        var secondArray = ByteArray(READ_BYTES)
        try {
            var fileInputStream = FileInputStream(tsFilePath)
            var len = fileInputStream.read(firstArray)
            while (len != -1) {
                len = fileInputStream.read(secondArray)
                if (findPacketLength(firstArray, secondArray)) {
                    break
                }
                System.arraycopy(secondArray, 0, firstArray, 0, READ_BYTES)
                mPacketStatLocation += READ_BYTES
            }
            fileInputStream.close()
            println(mPacketLong)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mPacketLong
    }

    private fun findPacketLength(firstArray: ByteArray, secondArray: ByteArray): Boolean {
        for (i in 0 until READ_BYTES) {
            if (firstArray[i] != TS_PACKET_HEAD_PREFIX.toByte()) {
                continue
            }
            if (checkNextNinth(i, firstArray, secondArray)) {
                return true
            }
        }
        return false
    }

    private fun checkNextNinth(i: Int, firstArray: ByteArray, secondArray: ByteArray): Boolean {
        var time = 1
        var startIndex = i

        while (startIndex < MAX_INDEX) {
            if (!selectArrayAndCheckCode(startIndex, firstArray, secondArray, TS_PACKAGE_LENGTH)) {
                break
            }
            time++
            startIndex += TS_PACKAGE_LENGTH
            if (time >= TS_PACKAGE_ENOUGH_TIME) {
                update(TS_PACKAGE_LENGTH, i)
                return true
            }
        }

        startIndex = i
        time = 1

        while (startIndex < MAX_INDEX) {
            if (!selectArrayAndCheckCode(
                    startIndex, firstArray, secondArray,
                    TS_PACKAGE_SUFFIX_LENGTH
                )
            ) {
                break
            }
            time++
            startIndex += TS_PACKAGE_SUFFIX_LENGTH
            if (time >= TS_PACKAGE_ENOUGH_TIME) {
                update(TS_PACKAGE_SUFFIX_LENGTH, i)
                return true
            }

        }
        return false
    }

    private fun selectArrayAndCheckCode(
        startIndex: Int,
        firstArray: ByteArray,
        secondArray: ByteArray,
        tsPackageLength: Int
    ): Boolean {
        if (firstArray[startIndex + tsPackageLength] != TS_PACKET_HEAD_PREFIX.toByte()
            && startIndex < READ_BYTES
        ) {
            return false
        }
        if (secondArray[startIndex + tsPackageLength] != TS_PACKET_HEAD_PREFIX.toByte()
            && startIndex >= READ_BYTES
        ) {
            return false
        }
        return true
    }

    private fun update(tsLength: Int, startIndex: Int) {
        mPacketLong = tsLength
        mPacketStatLocation += startIndex
    }

    fun distributePacket(filePath: String?) {
        if (mPacketLong <= 0) {
            return
        }
        try {
            val fileInputStream = FileInputStream(filePath)
            if (mPacketStatLocation > 0) {
                val skip = fileInputStream.skip(mPacketStatLocation)
                println("file skip: $skip")
            }
            val packetBuf = ByteArray(mPacketLong * 10)
            val packetData = ByteArray(mPacketLong)
            while (fileInputStream.read(packetBuf) != -1) {
                var i = 0
                while (i < packetBuf.size) {
                    val tsPacket = TsPacket()
                    System.arraycopy(packetBuf, i, packetData, 0, mPacketLong)
                    tsPacket.setObjectByByte(packetData)
                    setChanged()
                    this.notifyObservers(tsPacket)
                    i += mPacketLong
                }
                if (countObservers() <= 0) {
                    break
                }
            }
            fileInputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val READ_BYTES = 2048
        private const val TS_PACKAGE_LENGTH = 188
        private const val TS_PACKAGE_SUFFIX_LENGTH = 204
        private const val TS_PACKET_HEAD_PREFIX = 0x47
        private const val TS_PACKAGE_ENOUGH_TIME = 10
        private const val MAX_INDEX = 4096

        @Volatile
        var instance: TsPacketManager? = null

        @JvmName("getInstance1")
        fun getInstance(): TsPacketManager? {
            if (instance == null) {
                synchronized(this) {
                    instance = TsPacketManager()
                }
            }
            return instance
        }
    }
}
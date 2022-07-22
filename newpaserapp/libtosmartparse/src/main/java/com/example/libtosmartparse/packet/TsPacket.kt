package com.example.libtosmartparse.packet

import java.util.*
import kotlin.experimental.and

/**
 * @author xxm
 * 2022/7/17
 **/
class TsPacket {
    private var pid = 0
    private var transportScramblingControl = 0
    private var transportErrorIndicator = 0
    private var payloadUnitStartIndicator = 0
    private var priority = 0
    private var adaptationFieldControl = 0
    private var continuityCounter = 0
    private lateinit var payload: ByteArray

    fun getPid(): Int {
        return pid
    }

    fun getTransportScramblingControl(): Int {
        return transportScramblingControl
    }

    fun getTransportErrorIndicator(): Int {
        return transportErrorIndicator
    }

    fun getPayloadUnitStartIndicator(): Int {
        return payloadUnitStartIndicator
    }

    fun getPriority(): Int {
        return priority
    }

    fun getAdaptationFieldControl(): Int {
        return adaptationFieldControl
    }

    fun getContinuityCounter(): Int {
        return continuityCounter
    }

    fun getPayload(): ByteArray {
        return payload
    }

    override fun toString(): String {
        return "TsPacket{" +
                "pid=" + pid +
                ", transportScramblingControl=" + transportScramblingControl +
                ", transportErrorIndicator=" + transportErrorIndicator +
                ", payloadUnitStartIndicator=" + payloadUnitStartIndicator +
                ", priority=" + priority +
                ", adaptationFieldControl=" + adaptationFieldControl +
                ", continuityCounter=" + continuityCounter +
                ", payload=" + Arrays.toString(payload) +
                '}'
    }

    fun setObjectByByte(tsPacket: ByteArray) {
        pid =
            ((tsPacket[1] and REMOVE_LEFT_THREE_BIT) shl 8) or (tsPacket[2] and 0xFF)
        transportErrorIndicator = tsPacket[1] shr 7
        payloadUnitStartIndicator = tsPacket[1] shr 6 and CHECK_RIGHT_BIT
        priority = tsPacket[1] shr 5 and CHECK_RIGHT_BIT
        transportScramblingControl = (tsPacket[3] shr 6) and CHECK_RIGHT_TWO_BITS
        adaptationFieldControl = tsPacket[3] shr 4 and CHECK_RIGHT_TWO_BITS
        continuityCounter = (tsPacket[3] and CHECK_RIGHT_EIGHT).toInt()

        setPayload(tsPacket)
    }

    fun setPayload(data: ByteArray) {
        this.payload = ByteArray(data.size - 4)
        System.arraycopy(data, 4, this.payload, 0, data.size - 4)
    }

    companion object {
        const val CHECK_RIGHT_BIT: Byte = 0x01
        const val CHECK_RIGHT_TWO_BITS: Byte = 0x03
        const val CHECK_RIGHT_EIGHT: Byte = 0x0F
        const val REMOVE_LEFT_THREE_BIT: Byte = 0x1F
    }
}




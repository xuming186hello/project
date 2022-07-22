package com.example.libtosmartparse.packet

/**
 * @author xxm
 * 2022/7/17
 **/
infix fun Int.and(checkRightBit: Byte): Int {
    return this.and(checkRightBit.toInt())
}

infix fun Byte.and(byte: Byte): Int {
    return this.toInt() and (byte.toInt())
}

infix fun Byte.and(int: Int): Int {
    return this.toInt() and (int)
}

infix fun Byte.or(byte: Byte): Int {
    return this.toInt() or (byte.toInt())
}

infix fun Int.or(byte: Byte): Int {
    return this.or(byte.toInt())
}

infix fun Byte.shl(i: Int): Int {
    return this.toInt() shl i
}

infix fun Byte.shr(i: Int): Int {
    return this.toInt() shr i
}

infix fun Byte.shr(byte: Byte): Int {
    return this.toInt() shr byte.toInt()
}

infix fun Byte.shl(byte: Byte): Int {
    return this.toInt() shr byte.toInt()
}
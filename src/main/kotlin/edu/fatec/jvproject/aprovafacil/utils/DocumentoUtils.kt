package edu.fatec.jvproject.aprovafacil.utils

fun ByteArray.startsWithPdf(): Boolean {
    return this.size >= 4 &&
            this[0] == 0x25.toByte() &&
            this[1] == 0x50.toByte() &&
            this[2] == 0x44.toByte() &&
            this[3] == 0x46.toByte()
}
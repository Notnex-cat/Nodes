package com.notnex.nodes.util

import org.bouncycastle.jcajce.provider.digest.Keccak

fun generateNameFromHash(input: String): String {
    val digest = Keccak.Digest256()
    val hashBytes = digest.digest(input.toByteArray())
    val last20Bytes = hashBytes.takeLast(20)
    val hex = last20Bytes.joinToString("") { "%02x".format(it) }
    return "0x$hex"
}

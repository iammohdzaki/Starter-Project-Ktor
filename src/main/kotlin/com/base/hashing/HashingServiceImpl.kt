package com.base.hashing

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class HashingServiceImpl : HashingService {
    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        // Create a random salt using secure random
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        // Convert the byte range of salt to string using hex
        val saltAsHex = Hex.encodeHexString(salt)
        // Generate Hashed Password
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.salt + value) == saltedHash.hash
    }
}
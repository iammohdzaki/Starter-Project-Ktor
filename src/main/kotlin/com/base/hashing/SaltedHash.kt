package com.base.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
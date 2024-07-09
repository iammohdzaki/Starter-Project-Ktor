package com.data.request

data class UserRequest(
    val fullName: String = "",
    val email: String,
    val phoneNumber: String = "",
    val countryCode: String = "",
    val password: String,
    val salt: String = ""
)

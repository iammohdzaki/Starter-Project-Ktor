package com.data.response

data class UserResponse(
    val userId: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val countryCode: String,
    val createdOn: Long = System.currentTimeMillis(),
    var accessToken: String = ""
)

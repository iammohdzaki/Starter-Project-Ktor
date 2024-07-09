package com.data

import com.data.response.UserResponse
import org.bson.types.ObjectId
import org.bson.codecs.pojo.annotations.BsonId

data class User(
    @BsonId
    val userId: String = ObjectId().toString(),
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val countryCode: String,
    val createdOn: Long = System.currentTimeMillis(),
    val password: String,
    val salt: String
) {
    fun toUserResponse() =
        UserResponse(
            userId = userId,
            fullName = fullName,
            email = email,
            phoneNumber = phoneNumber,
            countryCode = countryCode,
            createdOn = createdOn
        )
}
package com.api.controllers

import com.api.sources.UserDataSource
import com.base.BaseResponse
import com.base.FailureResponse
import com.base.ServerConfig
import com.base.SuccessResponse
import com.base.hashing.HashingService
import com.base.hashing.SaltedHash
import com.base.jwt.TokenClaim
import com.base.jwt.service.TokenService
import com.data.User
import com.data.request.UserRequest
import io.ktor.http.*

class UserController(
    private val userDataSource: UserDataSource,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val appConfig: ServerConfig
) {

    suspend fun registerUser(userRequest: UserRequest): BaseResponse<Any> {
        if (!checkIfUserExist(userRequest.email)) {
            val saltedHash = hashingService.generateSaltedHash(userRequest.password)
            val user = User(
                fullName = userRequest.fullName,
                email = userRequest.email,
                phoneNumber = userRequest.phoneNumber,
                countryCode = userRequest.countryCode,
                password = saltedHash.hash,
                salt = saltedHash.salt
            )
            return if (userDataSource.insertUser(user)) {
                val token = tokenService.generateToken(
                    appConfig.tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = user.userId
                    )
                )
                val userResponse = user.toUserResponse()
                userResponse.accessToken = token
                SuccessResponse(
                    data = userResponse,
                    statusCode = HttpStatusCode.OK.value,
                    message = "User Created Successfully!"
                )
            } else {
                FailureResponse(
                    statusCode = HttpStatusCode.BadRequest.value,
                    message = "Something went wrong!",
                )
            }
        } else {
            return FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = "User Already Exists!",
            )
        }
    }

    suspend fun loginUser(userRequest: UserRequest): BaseResponse<Any> {
        val user = userDataSource.checkIfUserExists(userRequest.email)
        if (user != null) {
            val isValidPassword = hashingService.verify(
                userRequest.password,
                SaltedHash(
                    user.password,
                    user.salt
                )
            )
            if (isValidPassword) {
                val token = tokenService.generateToken(
                    appConfig.tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = user.userId
                    )
                )
                val userResponse = user.toUserResponse()
                userResponse.accessToken = token
                return SuccessResponse(
                    data = userResponse,
                    statusCode = HttpStatusCode.OK.value,
                    message = "Success"
                )
            } else {
                return FailureResponse(
                    statusCode = HttpStatusCode.BadRequest.value,
                    message = "Invalid Email!",
                )
            }
        } else {
            return FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = "Invalid Email!",
            )
        }
    }


    private suspend fun checkIfUserExist(email: String) =
        userDataSource.checkIfUserExists(email) != null
}
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
import com.locale.Locale
import com.locale.Strings
import com.utils.Keys
import io.ktor.http.*

class UserController(
    private val userDataSource: UserDataSource,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val appConfig: ServerConfig
) {

    suspend fun registerUser(userRequest: UserRequest, language: String = "en"): BaseResponse<Any> {
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
                        name = Keys.USER_ID,
                        value = user.userId
                    )
                )
                val userResponse = user.toUserResponse()
                userResponse.accessToken = token
                SuccessResponse(
                    data = userResponse,
                    statusCode = HttpStatusCode.OK.value,
                    message = Locale.getString(Strings.USER_CREATED_SUCCESSFULLY, language)
                )
            } else {
                FailureResponse(
                    statusCode = HttpStatusCode.BadRequest.value,
                    message = Locale.getString(Strings.SOMETHING_WENT_WRONG, language),
                )
            }
        } else {
            return FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = Locale.getString(Strings.USER_ALREADY_EXISTS, language),
            )
        }
    }

    suspend fun loginUser(userRequest: UserRequest, language: String = "en"): BaseResponse<Any> {
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
                        name = Keys.USER_ID,
                        value = user.userId
                    )
                )
                val userResponse = user.toUserResponse()
                userResponse.accessToken = token
                return SuccessResponse(
                    data = userResponse,
                    statusCode = HttpStatusCode.OK.value,
                    message = Locale.getString(Strings.SUCCESS, language)
                )
            } else {
                return FailureResponse(
                    statusCode = HttpStatusCode.BadRequest.value,
                    message = Locale.getString(Strings.INVALID_PASSWORD, language),
                )
            }
        } else {
            return FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = Locale.getString(Strings.INVALID_EMAIL, language),
            )
        }
    }


    suspend fun getUserProfile(userId: String, language: String = "en"): BaseResponse<Any> {
        val user = userDataSource.getUser(userId)
        return if (user != null) {
            val userResponse = user.toUserResponse()
            SuccessResponse(
                data = userResponse,
                statusCode = HttpStatusCode.OK.value,
                message = Locale.getString(Strings.SUCCESS, language)
            )
        } else {
            FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = Locale.getString(Strings.SOMETHING_WENT_WRONG, language),
            )
        }
    }

    suspend fun editProfile(userId: String, userRequest: UserRequest, language: String = "en"): BaseResponse<Any> {
        val user = userDataSource.getUser(userId)
        return if (user != null) {
            val updatedUser = user.copy(
                fullName = userRequest.fullName,
                email = userRequest.email,
                phoneNumber = userRequest.phoneNumber,
                countryCode = userRequest.countryCode
            )
            if (userDataSource.updateUser(updatedUser)) {
                SuccessResponse(
                    data = updatedUser.toUserResponse(),
                    statusCode = HttpStatusCode.OK.value,
                    message = Locale.getString(Strings.SUCCESS, language)
                )
            } else {
                FailureResponse(
                    statusCode = HttpStatusCode.BadRequest.value,
                    message = Locale.getString(Strings.SOMETHING_WENT_WRONG, language)
                )
            }
        } else {
            FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = Locale.getString(Strings.SOMETHING_WENT_WRONG, language)
            )
        }
    }

    private suspend fun checkIfUserExist(email: String) =
        userDataSource.checkIfUserExists(email) != null
}
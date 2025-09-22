package com.api

object Endpoints {
    private const val API_VERSION = "/v1"

    /**
     * ======= User Authentication ========
     */
    private const val USER = "$API_VERSION/user"
    const val USER_REGISTER_REQUEST = "$USER/register"
    const val USER_LOGIN_REQUEST = "$USER/login"
    const val USER_LOGIN_REQUEST_WITH_TOKEN = "$USER/loginViaAccessToken"
    const val USER_PROFILE_REQUEST = "$USER/getUserProfile"
    const val USER_PROFILE_EDIT_REQUEST = "$USER/updateUserProfile"
    const val CHAT_LLM = "$USER/chatLLM"

    /**
    * ======= Configuration ========
    */
    const val GET_CONFIG = "$API_VERSION/getConfiguration"
    const val UPDATE_CONFIG = "$API_VERSION/updateConfiguration"
}

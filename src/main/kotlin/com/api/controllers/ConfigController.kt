package com.api.controllers

import com.api.sources.ConfigDataSource
import com.base.BaseResponse
import com.base.FailureResponse
import com.base.SuccessResponse
import com.locale.Locale
import com.locale.Strings
import com.data.Configuration
import com.utils.Database
import io.ktor.http.HttpStatusCode

class ConfigController(
    private val configDataSource: ConfigDataSource
) {

    suspend fun getConfigurations(language: String = "en"): BaseResponse<Any> {
        val config = configDataSource.getConfiguration()
        return SuccessResponse(
            data = config,
            statusCode = HttpStatusCode.OK.value,
            message = Locale.getString(Strings.SUCCESS, language)
        )
    }

    suspend fun updateConfiguration(configuration: Configuration, language: String = "en"): BaseResponse<Any> {
        val updatedConfig = configuration.copy(
            _id = Database.CONFIGURATION_DOCUMENT
        )
        return if (configDataSource.updateConfiguration(updatedConfig)) {
            SuccessResponse(
                data = updatedConfig,
                statusCode = HttpStatusCode.OK.value,
                message = Locale.getString(Strings.SUCCESS, language)
            )
        } else {
            FailureResponse(
                statusCode = HttpStatusCode.BadRequest.value,
                message = Locale.getString(Strings.SOMETHING_WENT_WRONG, language)
            )
        }
    }
}
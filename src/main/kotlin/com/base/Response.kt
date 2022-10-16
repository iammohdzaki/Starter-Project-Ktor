package com.base

interface BaseResponse<T : Any>

data class SuccessResponse<T : Any>(
    val statusCode: Int,
    val data: T? = null,
    val message: String? = null
) : BaseResponse<T>

data class FailureResponse<T : Any>(
    val statusCode: Int,
    val message: String? = null
) : BaseResponse<T>

data class PaginatedResponse<T : Any>(
    val statusCode: Int,
    val prev: Int?,
    val next: Int?,
    val totalCount: Int = 0,
    val totalPages: Int = 0,
    val data: T? = null,
    val message: String? = null
) : BaseResponse<T>

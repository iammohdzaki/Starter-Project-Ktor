package com.api.sources

import com.data.Configuration

interface ConfigDataSource {

    suspend fun getConfiguration(): Configuration

    suspend fun updateConfiguration(configuration: Configuration): Boolean
}
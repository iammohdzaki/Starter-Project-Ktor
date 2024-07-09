package com.api.sources

import com.data.Configuration
import com.utils.Database
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ConfigDataSourceImpl(
    db: CoroutineDatabase
) : ConfigDataSource {
    private val configCollection = db.getCollection<Configuration>(Database.CONFIGURATION_COLLECTION)

    override suspend fun getConfiguration(): Configuration {
        return configCollection.findOne(Configuration::_id eq Database.CONFIGURATION_DOCUMENT)!!
    }

    override suspend fun updateConfiguration(configuration: Configuration): Boolean {
        return configCollection.updateOneById(Configuration::_id eq configuration._id, configuration).wasAcknowledged()
    }
}

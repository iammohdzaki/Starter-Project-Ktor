package com.di

import com.utils.Database
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine

val databaseModule = module {
    single {
        if (System.getenv("DB_URL").isNullOrEmpty()) {
            KMongo.createClient()
                .coroutine
                .getDatabase(Database.DATABASE_NAME)
        } else {
            KMongo.createClient(connectionString = System.getenv("DB_URL"))
                .coroutine
                .getDatabase(Database.DATABASE_NAME)
        }
    }
}

package com.api.sources

import com.data.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserDataSourceImpl(
    db: CoroutineDatabase
) : UserDataSource {

    private val userCollection = db.getCollection<User>()

    override suspend fun insertUser(user: User) =
        userCollection.insertOne(user).wasAcknowledged()

    override suspend fun updateUser(user: User) =
        userCollection.updateOneById(user.userId, user).wasAcknowledged()

    override suspend fun checkIfUserExists(email: String) =
        userCollection.findOne(User::email eq email)

    override suspend fun deleteUser(userId: String) =
        userCollection.deleteOne(userId).wasAcknowledged()

    override suspend fun getUser(userId: String) =
        userCollection.findOne(User::userId eq userId)

    override suspend fun getUsers() =
        userCollection.find().toList()
}

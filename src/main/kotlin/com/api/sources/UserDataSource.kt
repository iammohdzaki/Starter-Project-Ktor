package com.api.sources

import com.data.User

interface UserDataSource {

    suspend fun insertUser(user: User): Boolean

    suspend fun updateUser(user: User): Boolean

    suspend fun deleteUser(userId: String): Boolean

    suspend fun checkIfUserExists(email: String): User?

    suspend fun getUser(userId: String): User?

    suspend fun getUsers(): List<User>
}
package com.chitchathub.theappmakerbuddy.data.user.datasource

import com.chitchathub.theappmakerbuddy.data.user.model.User
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.toList

class MongoUserDataSource(
    mongoDatabase: MongoDatabase
): UserDataSource {

    companion object {
        const val USER_COLLECTION = "users"
    }

    val users = mongoDatabase.getCollection<User>(USER_COLLECTION)
    var user: User? = null
    override suspend fun getUsers(): List<User> {
        return users.find<User>().toList()
    }

    override suspend fun getUserById(userId: String): User? {

        users.find<User>(filter = Filters.eq(User::userId.name,userId)).filterNotNull().collect{
            user = it
        }
        return user
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        val filter = Filters.and(
            listOf(
                Filters.eq(User::email.name,email),
                Filters.eq(User::password.name,password)
            )
        )
        users.find<User>(filter = filter).filterNotNull().collect{
            user = it
        }
        return user
    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        val filter = Filters.and(
            listOf(
                Filters.eq(User::username.name,username),
                Filters.eq(User::password.name,password)
            )
        )
        users.find<User>(filter = filter).filterNotNull().collect{
            user = it
        }
        return user
    }

    override suspend fun registerUser(user: User): Boolean {
         return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun updateUser(userId: String, user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserProfilePhoto(userId: String, userProfileUrl: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return users.deleteOne(Filters.eq(User::userId.name,userId)).wasAcknowledged()
    }
}
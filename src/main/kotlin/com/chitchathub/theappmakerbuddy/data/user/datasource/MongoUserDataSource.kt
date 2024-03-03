package com.chitchathub.theappmakerbuddy.data.user.datasource

import com.chitchathub.theappmakerbuddy.data.user.model.User
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
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
        val updatedUser = user.copy(userId = userId)
        val result = users.findOneAndReplace(Filters.eq(User::userId.name, userId), updatedUser)
        if (result != null) {
            return updatedUser
        } else {
            throw NoSuchElementException("User not found with ID: $userId")
        }
    }

    override suspend fun updateUserProfilePhoto(userId: String, userProfileUrl: String): User {
        val user = users.find(Filters.eq(User::userId.name, userId)).first()

        val updatedUser = user.copy(userProfilePhoto = userProfileUrl)
        users.replaceOne(Filters.eq(User::userId.name, userId), updatedUser)
        return updatedUser
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return users.deleteOne(Filters.eq(User::userId.name,userId)).wasAcknowledged()
    }
}
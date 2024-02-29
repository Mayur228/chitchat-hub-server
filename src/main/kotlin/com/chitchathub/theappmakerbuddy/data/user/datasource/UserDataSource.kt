package com.chitchathub.theappmakerbuddy.data.user.datasource

import com.chitchathub.theappmakerbuddy.data.user.model.User

interface UserDataSource {
    suspend fun getUsers(): List<User>?
    suspend fun getUserById(userId: String): User?
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?
    suspend fun registerUser(user: User): Boolean
    suspend fun updateUser(userId: String, user: User): User
    suspend fun updateUserProfilePhoto(userId: String, userProfileUrl: String): User
    suspend fun deleteUser(userId: String): Boolean
}
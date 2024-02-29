package com.chitchathub.theappmakerbuddy.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: String,
    val username: String,
    val userProfilePhoto: String,
    val email: String,
    val phone: String,
    val password: String
)

val users = mutableListOf<User>(
    User(
        userId = "a1",
        username = "abc",
        userProfilePhoto = "",
        email = "abc@gmail.com",
        phone = "+91 7095763958",
        password = "abc@123"
    )
)
package com.chitchathub.theappmakerbuddy.data.user.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId
    val userId: String = ObjectId().toString(),
    val username: String,
    val userProfilePhoto: String,
    val email: String,
    val phone: String,
    val password: String
)
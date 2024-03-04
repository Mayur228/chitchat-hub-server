package com.chitchathub.theappmakerbuddy.data.chat.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Message(
    @BsonId
    val id: String = ObjectId().toString(),
    val roomId: String,
    val messageId: String,
    val senderId: String,
    val username: String,
    val userPP: String,
    val message: String,
    val messageType: MessageType,
    val messageTime: String = System.currentTimeMillis().toString(),
    val isMessageRead: Boolean,
    val isOnline: Boolean
)


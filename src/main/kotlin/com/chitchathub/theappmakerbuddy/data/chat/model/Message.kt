package com.chitchathub.theappmakerbuddy.data.chat.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Message(
    @BsonId
    val id: ObjectId = ObjectId.get(),
    val roomId: String,
    val messageId: String,
    val senderId: String,
    val receiverId: String,
    val username: String,
    val userPP: String,
    val message: String,
    val messageType: MessageType,
    val messageTime: String,
    val isMessageRead: Boolean,
    val isOnline: String
)


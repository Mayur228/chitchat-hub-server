package com.chitchathub.theappmakerbuddy.data.chat.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/*data class Chat(
    val roomId: String,
    val username: String,
    val userPP: String,
    val lastMessage: String,
    val messageTime: String,
    val isMessageRead: Boolean,
    val unReadMessageCount: Int,
    val isOnline: Boolean
)*/
data class Room(
    @BsonId
    val id: ObjectId = ObjectId.get(),
    val roomId: String,
    val username: String,
    val userPP: String,
    val messages: MutableList<Message> = mutableListOf()
)
package com.chitchathub.theappmakerbuddy.data.chat.model

import io.ktor.websocket.*
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Room(
    @BsonId
    val id: ObjectId = ObjectId.get(),
    val roomId: String,
    val username: String,
    val userPP: String,
    val messages: MutableList<Message> = mutableListOf(),
    val socket: WebSocketSession
)
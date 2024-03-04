package com.chitchathub.theappmakerbuddy.data.chat.datasource

import com.chitchathub.theappmakerbuddy.data.chat.model.Message
import com.chitchathub.theappmakerbuddy.data.chat.model.Room
import io.ktor.server.websocket.*
import io.ktor.websocket.*

interface ChatDataSource {
    suspend fun getChat(): List<Room>

    suspend fun getMessage(roomId: String): List<Message>

    suspend fun sendMessage(userId: String, roomId: String,message: String,socket: WebSocketSession): Boolean
    suspend fun deleteMessage(messageId: String): Boolean

    suspend fun deleteChat(roomId: String): Boolean
}
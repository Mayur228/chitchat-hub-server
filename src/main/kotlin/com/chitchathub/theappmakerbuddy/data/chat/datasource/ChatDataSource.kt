package com.chitchathub.theappmakerbuddy.data.chat.datasource

import com.chitchathub.theappmakerbuddy.data.chat.model.Message
import com.chitchathub.theappmakerbuddy.data.chat.model.Room

interface ChatDataSource {
    suspend fun getChat(): List<Room>

    suspend fun getMessage(roomId: String): List<Message>

    suspend fun sendMessage(message: Message): Boolean

    suspend fun deleteMessage(messageId: String): Boolean

    suspend fun deleteChat(roomId: String): Boolean
}
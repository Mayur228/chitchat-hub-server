package com.chitchathub.theappmakerbuddy.data.chat.datasource

import com.chitchathub.theappmakerbuddy.data.chat.model.Chat
import com.chitchathub.theappmakerbuddy.data.chat.model.Message

interface ChatDataSource {
    suspend fun getChat(): List<Chat>

    suspend fun getMessage(roomId: String): List<Message>

    suspend fun sendMessage(message: Message): Boolean

    suspend fun deleteMessage(messageId: String): Boolean

    suspend fun deleteChat(roomId: String): Boolean
}
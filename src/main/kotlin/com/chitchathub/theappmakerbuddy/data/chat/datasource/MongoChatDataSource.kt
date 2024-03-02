package com.chitchathub.theappmakerbuddy.data.chat.datasource

import com.chitchathub.theappmakerbuddy.data.chat.model.Chat
import com.chitchathub.theappmakerbuddy.data.chat.model.Message

class MongoChatDataSource: ChatDataSource {
    override suspend fun getChat(): List<Chat> {
        TODO("Not yet implemented")
    }

    override suspend fun getMessage(roomId: String): List<Message> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(message: Message): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(messageId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChat(roomId: String): Boolean {
        TODO("Not yet implemented")
    }
}
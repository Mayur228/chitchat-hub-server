package com.chitchathub.theappmakerbuddy.data.chat.datasource

import com.chitchathub.theappmakerbuddy.data.chat.model.Chat

interface ChatDataSource {
    suspend fun getChat(): List<Chat>
}
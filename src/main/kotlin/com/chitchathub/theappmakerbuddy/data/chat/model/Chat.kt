package com.chitchathub.theappmakerbuddy.data.chat.model

data class Chat(
    val roomId: String,
    val username: String,
    val userPP: String,
    val lastMessage: String,
    val messageTime: String,
    val isMessageRead: Boolean,
    val unReadMessageCount: Int,
)

val ChatList = mutableListOf<Chat>()

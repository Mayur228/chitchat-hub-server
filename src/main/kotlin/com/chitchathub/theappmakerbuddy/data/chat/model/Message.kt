package com.chitchathub.theappmakerbuddy.data.chat.model

data class Message(
    val senderId: String,
    val receiverId: String,
    val username: String,
    val userPP: String,
    val message: String,
    val messageTime: String,
    val isMessageRead: Boolean
)

val messageList = mutableListOf<Message>()

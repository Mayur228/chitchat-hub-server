package com.chitchathub.theappmakerbuddy.data.chat.model

data class Message(
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

val messageList = mutableListOf<Message>()

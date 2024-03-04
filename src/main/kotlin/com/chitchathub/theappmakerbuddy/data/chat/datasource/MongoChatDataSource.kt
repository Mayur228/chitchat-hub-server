package com.chitchathub.theappmakerbuddy.data.chat.datasource

import com.chitchathub.theappmakerbuddy.data.chat.model.Message
import com.chitchathub.theappmakerbuddy.data.chat.model.MessageType
import com.chitchathub.theappmakerbuddy.data.chat.model.Room
import com.chitchathub.theappmakerbuddy.data.user.datasource.MongoUserDataSource.Companion.USER_COLLECTION
import com.chitchathub.theappmakerbuddy.data.user.model.User
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.toList
import java.util.*

class MongoChatDataSource(
    mongoDatabase: MongoDatabase
): ChatDataSource {

    companion object {
        const val MESSAGE_COLLECTION = "message"
        const val ROOM_COLLECTION = "message"
    }

    val messages = mongoDatabase.getCollection<Message>(MESSAGE_COLLECTION)
    val rooms = mongoDatabase.getCollection<Room>(ROOM_COLLECTION)
    val users = mongoDatabase.getCollection<Room>(USER_COLLECTION)

    override suspend fun getChat(): List<Room> {
        return rooms.find<Room>().toList()
    }

    override suspend fun getMessage(roomId: String): List<Message> {
        return messages.find<Message>(Filters.eq(Message::roomId.name,roomId)).toList().sortedByDescending {
            it.messageTime
        }
    }

    override suspend fun sendMessage(userId: String, roomId: String,message: String, socket: WebSocketSession): Boolean {
        val room = rooms.find(Filters.eq(Message::roomId.name, roomId)).toList().firstOrNull()
        val username = users.find(Filters.eq(User::userId.name,userId)).toList().first().username
        val userPP = users.find(Filters.eq(User::userId.name,userId)).toList().first().userPP

        val messageData = Message(
            senderId = userId,
            username = username,
            userPP = userPP,
            messageId = UUID.randomUUID().toString(),
            message = message,
            roomId = roomId,
            messageType = MessageType.TEXT,
            isMessageRead = false,
            isOnline = true
        )
        return if (room == null) {
            val newRoom = Room(roomId = messageData.roomId, username = username, userPP = userPP, socket = socket, messages =  mutableListOf(messageData))
            rooms.insertOne(newRoom).wasAcknowledged()
        } else {
            val updatedRoom = room.copy(messages = room.messages.toMutableList().apply { add(messageData) })
            rooms.replaceOne(Filters.eq(Message::roomId.name, messageData.roomId), updatedRoom).wasAcknowledged()
        }
    }

    override suspend fun deleteMessage(messageId: String): Boolean {
        return messages.deleteOne(Filters.eq(Message::messageId.name,messageId)).wasAcknowledged()
    }

    override suspend fun deleteChat(roomId: String): Boolean {
        return rooms.deleteOne(Filters.eq(Message::roomId.name,roomId)).wasAcknowledged()
    }
}
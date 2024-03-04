package com.chitchathub.theappmakerbuddy.data.chat.controller

import com.chitchathub.theappmakerbuddy.data.chat.datasource.MongoChatDataSource
import com.chitchathub.theappmakerbuddy.data.chat.model.Message
import com.chitchathub.theappmakerbuddy.data.chat.model.MessageType
import com.chitchathub.theappmakerbuddy.data.chat.model.Room
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(private val mongoChatDataSource: MongoChatDataSource) {
    private val rooms = ConcurrentHashMap<String, Room>()
    fun onJoin(
        userId: String,
        username: String,
        userPP: String,
        roomId: String,
        socket: WebSocketSession
    ) {

        rooms[username] = Room(
            username = username,
            userPP = userPP,
            roomId = roomId,
            socket = socket
        )
    }

    suspend fun sendMessage(userId: String, roomId: String, message: String) {
        rooms.values.forEach { member ->
            mongoChatDataSource.sendMessage(userId,roomId,message,member.socket)

            val parsedMessage = Json.encodeToString(message)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(roomId: String): List<Message> {
        return mongoChatDataSource.getMessage(roomId)
    }

    suspend fun tryDisconnect(username: String) {
        rooms[username]?.socket?.close()
        if(rooms.containsKey(username)) {
            rooms.remove(username)
        }
    }
}
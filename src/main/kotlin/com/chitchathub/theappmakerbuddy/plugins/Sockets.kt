package com.chitchathub.theappmakerbuddy.plugins

import com.chitchathub.theappmakerbuddy.data.chat.controller.RoomController
import com.chitchathub.theappmakerbuddy.data.chat.datasource.MongoChatDataSource
import com.chitchathub.theappmakerbuddy.data.chat.model.ChatSession
import com.chitchathub.theappmakerbuddy.mongodb.configureMongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.time.Duration

fun Application.configureSockets() {
    val roomController = RoomController(MongoChatDataSource(configureMongoDatabase()))
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/ws/chat") {

            val session = call.sessions.get<ChatSession>()
            if(session == null) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
                return@webSocket
            }
            try {
                roomController.onJoin(
                    userId = session.userId,
                    username = session.username,
                    userPP = session.userPP,
                    roomId = session.sessionId,
                    socket = this
                )
                incoming.consumeEach { frame ->
                    if(frame is Frame.Text) {
                        roomController.sendMessage(
                            userId = session.userId,
                            roomId = session.sessionId,
                            message = frame.readText()
                        )
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
            } finally {
                roomController.tryDisconnect(session.username)
            }
        }
    }
}

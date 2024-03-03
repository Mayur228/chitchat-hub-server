package com.chitchathub.theappmakerbuddy.routes

import com.chitchathub.theappmakerbuddy.data.chat.datasource.MongoChatDataSource
import com.chitchathub.theappmakerbuddy.data.chat.model.Message
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.chatRoutes(
    userDataSource: MongoChatDataSource
) {
    route("/chats") {
        get {
            val chatRooms = userDataSource.getChat().toList()
            call.respond(chatRooms)
        }

        delete("{roomId}") {
            val roomId = call.parameters["roomId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing roomId")
            if (userDataSource.deleteChat(roomId)) {
                call.respond(HttpStatusCode.Accepted, "Room deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Room not found")
            }
        }
    }

    route("/message") {
        // Send message
        post("{roomId}") {
            val roomId = call.parameters["roomId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing roomId")
            val message = call.receive<Message>()
            if (userDataSource.sendMessage(message)) {
                call.respond(HttpStatusCode.Created, "Message sent")
            } else {
                call.respond(HttpStatusCode.NotFound, "Room not found")
            }
        }

        // Delete message
        delete("{messageId}") {
            val roomId = call.parameters["roomId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing roomId")
            val messageId = call.parameters["messageId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing messageId")
            if (userDataSource.deleteMessage(messageId)) {
                call.respond(HttpStatusCode.Accepted, "Message deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Message not found")
            }
        }

    }
}


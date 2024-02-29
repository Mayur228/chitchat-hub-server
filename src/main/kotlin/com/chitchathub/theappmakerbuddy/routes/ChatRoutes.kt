package com.chitchathub.theappmakerbuddy.routes

import com.chitchathub.theappmakerbuddy.data.chat.model.ChatList
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.chatRoutes(){
    route("/chats"){
        get("{roomId}") {
            val id = call.parameters["roomId"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val chat = ChatList.find { it.roomId == id } ?: return@get call.respondText("No User Found", status = HttpStatusCode.NotFound)

        }
        post {

        }
        delete {

        }
    }
}
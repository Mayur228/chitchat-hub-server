package com.chitchathub.theappmakerbuddy.routes

import com.chitchathub.theappmakerbuddy.model.User
import com.chitchathub.theappmakerbuddy.model.users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting() {
    route("/user"){
        get {
            if(users.isNotEmpty()){
                call.respond(users)
            }
            else{
                call.respondText("No user found", status = HttpStatusCode.OK)
            }
        }

        get ("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val user = users.find { it.userId == id } ?: return@get call.respondText("No User Found", status = HttpStatusCode.NotFound)

            call.respond(user)
        }

        post {
            val user = call.receive<User>()
            users.add(user)
            call.respondText("User Registered", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (users.removeIf{it.userId == id}){
                call.respondText("User Deleted", status = HttpStatusCode.Accepted)
            }else{
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
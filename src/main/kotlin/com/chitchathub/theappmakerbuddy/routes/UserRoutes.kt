package com.chitchathub.theappmakerbuddy.routes

import com.chitchathub.theappmakerbuddy.data.user.datasource.MongoUserDataSource
import com.chitchathub.theappmakerbuddy.data.user.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting(
    userDataSource: MongoUserDataSource
) {
    route("/user"){
        get {
            val user = userDataSource.getUsers()
            if(user.isNotEmpty()){
                call.respond(user)
            }else{
                call.respondText("No user found", status = HttpStatusCode.OK)
            }
        }

        get("{email}/{password}") {
            val email = call.parameters["email"] ?: return@get call.respondText("Wrong Email", status = HttpStatusCode.BadRequest)
            val password = call.parameters["password"] ?: return@get call.respondText("Wrong Password", status = HttpStatusCode.BadRequest)
            val user = userDataSource.getUserByEmailAndPassword(email, password) ?: return@get call.respondText("No User Found", status = HttpStatusCode.NotFound)
            call.respond(user)
        }

        get("{username}/{password}") {
            val username = call.parameters["username"] ?: return@get call.respondText("Wrong Username", status = HttpStatusCode.BadRequest)
            val password = call.parameters["password"] ?: return@get call.respondText("Wrong Password", status = HttpStatusCode.BadRequest)
            val user = userDataSource.getUserByUsernameAndPassword(username, password) ?: return@get call.respondText("No User Found", status = HttpStatusCode.NotFound)
            call.respond(user)
        }

        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val user = userDataSource.getUserById(id) ?: return@get call.respondText("No User Found", status = HttpStatusCode.NotFound)
            call.respond(user)
        }

        post{
            val user = call.receive<User>()
            userDataSource.registerUser(user)
            call.respondText("User Registered", status = HttpStatusCode.Created)
        }

        delete("{id?}") {

            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (userDataSource.deleteUser(id)){
                call.respondText("User Deleted", status = HttpStatusCode.Accepted)
            }else{
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
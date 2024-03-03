package com.chitchathub.theappmakerbuddy.plugins

import com.chitchathub.theappmakerbuddy.data.chat.datasource.MongoChatDataSource
import com.chitchathub.theappmakerbuddy.data.user.datasource.MongoUserDataSource
import com.chitchathub.theappmakerbuddy.mongodb.configureMongoDatabase
import com.chitchathub.theappmakerbuddy.routes.chatRoutes
import com.chitchathub.theappmakerbuddy.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val userDataSource = MongoUserDataSource(configureMongoDatabase())
    val chatDataSource = MongoChatDataSource(configureMongoDatabase())
    routing {
       userRouting(userDataSource)
        chatRoutes(chatDataSource)
    }
}

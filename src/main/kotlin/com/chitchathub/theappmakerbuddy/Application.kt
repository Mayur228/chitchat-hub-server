package com.chitchathub.theappmakerbuddy

import com.chitchathub.theappmakerbuddy.mongodb.configureMongoDatabase
import com.chitchathub.theappmakerbuddy.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
//    configureSecurity()
    configureMongoDatabase()
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()
}

package com.chitchathub.theappmakerbuddy.mongodb

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.*

fun Application.configureMongoDatabase(): MongoDatabase {
//    val connectionString = "mongodb+srv://mayursinh228:Chino62987@cluster0.d2pt5pz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"

    val client = MongoClient.create(connectionString = System.getenv("MONGO_URI"))
    return  client.getDatabase(databaseName = "chitchat_hub")
}
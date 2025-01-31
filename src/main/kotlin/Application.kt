package com.example

import com.example.db.configureDatabases
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain
        .main(args)
}

fun Application.module() {
    configureHTTP()
    configureSerialization()
    val database: Database = configureDatabases()
    configureRouting(database)
}

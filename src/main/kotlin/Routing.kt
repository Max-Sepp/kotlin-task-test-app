package com.example

import com.example.models.DBTaskService
import com.example.routes.taskRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun Application.configureRouting(database: Database) {
    val taskService = DBTaskService(database)

    taskRoutes(taskService)

    routing {
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}

package com.example.routes

import com.example.models.ExposedTask
import com.example.models.TaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.taskRoutes(taskService: TaskService) {
    routing {
        route("/todos") {
            // Create a new Task
            post {
                val todo = call.receive<ExposedTask>()

                val todoId: Int = taskService.create(todo)

                call.respond(HttpStatusCode.Created, ExposedTask(todoId, todo.title, todo.completed))
            }

            // Get all ToDos
            get {
                val todos = taskService.readAll()

                call.respond(todos)
            }

            // Get a specific Task by ID
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(emptyList<ExposedTask>())

                val todo = taskService.read(id)

                if (todo != null) {
                    call.respond(todo)
                } else {
                    call.respond(emptyList<ExposedTask>())
                }
            }

            // Update a Task
            put("/{id}") {
                val id: Int = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(emptyList<ExposedTask>())
                val newTodo: ExposedTask = call.receive<ExposedTask>()

                taskService.update(id, newTodo)

                call.respond(newTodo)
            }

            // Delete a Task
            delete("/{id}") {
                val id: Int =
                    call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(emptyList<ExposedTask>())

                taskService.delete(id)

                call.respond("ToDo with ID $id deleted")
            }
        }
    }
}

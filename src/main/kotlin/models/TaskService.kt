package com.example.models

interface TaskService {
    suspend fun create(task: ExposedTask): Int

    suspend fun read(id: Int): ExposedTask?

    suspend fun readAll(): List<ExposedTask>

    suspend fun update(
        id: Int,
        task: ExposedTask,
    )

    suspend fun delete(id: Int)
}

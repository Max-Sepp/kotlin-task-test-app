package com.example.models

import com.example.db.tables.Task
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class DBTaskService(
    database: Database,
) : TaskService {
    init {
        transaction(database) {
            SchemaUtils.create(Task)
        }
    }

    override suspend fun create(task: ExposedTask): Int =
        dbQuery {
            Task.insert {
                it[title] = task.title
                it[completed] = task.completed
            }[Task.id]
        }

    override suspend fun read(id: Int): ExposedTask? =
        dbQuery {
            Task
                .selectAll()
                .where { Task.id eq id }
                .map {
                    ExposedTask(
                        it[Task.id],
                        it[Task.title],
                        it[Task.completed],
                    )
                }.firstOrNull()
        }

    override suspend fun readAll(): List<ExposedTask> =
        dbQuery {
            Task
                .selectAll()
                .map {
                    ExposedTask(
                        it[Task.id],
                        it[Task.title],
                        it[Task.completed],
                    )
                }
        }

    override suspend fun update(
        id: Int,
        task: ExposedTask,
    ) {
        dbQuery {
            Task
                .update({ Task.id eq id }) {
                    it[title] = task.title
                    it[completed] = task.completed
                }
        }
    }

    override suspend fun delete(id: Int) {
        dbQuery {
            Task.deleteWhere { Task.id eq id }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}

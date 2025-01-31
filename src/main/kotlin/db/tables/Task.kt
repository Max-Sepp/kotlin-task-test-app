package com.example.db.tables

import org.jetbrains.exposed.sql.Table

object Task : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", length = 50)
    val completed = bool("completed")

    override val primaryKey = PrimaryKey(id)
}

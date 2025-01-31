package com.example.db

import org.jetbrains.exposed.sql.*

// currently using h2 in memory database
fun configureDatabases(): Database =
    Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )

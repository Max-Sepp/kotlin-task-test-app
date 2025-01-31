package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ExposedTask(
    val id: Int? = null,
    val title: String,
    val completed: Boolean,
)

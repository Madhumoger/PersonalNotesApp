package com.personal.notes.data.model

import kotlinx.datetime.Instant

data class Note(
    val id: Long = 0,
    val title: String,
    val body: String,
    val createdDate: Instant
)

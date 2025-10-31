package com.personal.madNotex.feature.notes.data.repository

import com.personal.madNotex.feature.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun deleteNote(id: Long)
}


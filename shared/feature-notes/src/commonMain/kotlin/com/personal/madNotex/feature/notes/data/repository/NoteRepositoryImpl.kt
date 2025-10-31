package com.personal.madNotex.feature.notes.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.database.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

class NoteRepositoryImpl(
    private val database: NotesDatabase
) : NoteRepository {

    private val queries = database.noteQueries

    override fun getAllNotes(): Flow<List<Note>> {
        return queries.getAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO) {
        queries.getNoteById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insertNote(note: Note): Long = withContext(Dispatchers.IO) {
        queries.insertNote(
            title = note.title,
            body = note.body,
            createdDate = note.createdDate.toEpochMilliseconds()
        )
        queries.getLastInsertedId().executeAsOne()
    }

    override suspend fun deleteNote(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteNoteById(id)
    }

    private fun com.personal.madNotex.database.NoteEntity.toDomain(): Note {
        return Note(
            id = this.id,
            title = this.title,
            body = this.body,
            createdDate = Instant.fromEpochMilliseconds(this.createdDate)
        )
    }
}


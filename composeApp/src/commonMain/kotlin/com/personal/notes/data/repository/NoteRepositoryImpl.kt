package com.personal.notes.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.personal.notes.data.model.Note
import com.personal.notes.database.NotesDatabase
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
                    Note(
                        id = entity.id,
                        title = entity.title,
                        body = entity.body,
                        createdDate = Instant.fromEpochMilliseconds(entity.createdDate)
                    )
                }
            }
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO) {
        queries.getNoteById(id).executeAsOneOrNull()?.let { entity ->
            Note(
                id = entity.id,
                title = entity.title,
                body = entity.body,
                createdDate = Instant.fromEpochMilliseconds(entity.createdDate)
            )
        }
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
}

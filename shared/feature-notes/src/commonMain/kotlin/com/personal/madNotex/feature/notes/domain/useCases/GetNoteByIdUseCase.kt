package com.personal.madNotex.feature.notes.domain.useCases

import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.feature.notes.data.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Long): Result<Note?> {
        return try {
            val note = repository.getNoteById(noteId)
            Result.success(note)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

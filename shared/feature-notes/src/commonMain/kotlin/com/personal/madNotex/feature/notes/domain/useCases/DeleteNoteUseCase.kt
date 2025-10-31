package com.personal.madNotex.feature.notes.domain.useCases

import com.personal.madNotex.feature.notes.data.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Long): Result<Unit> {
        return try {
            repository.deleteNote(noteId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


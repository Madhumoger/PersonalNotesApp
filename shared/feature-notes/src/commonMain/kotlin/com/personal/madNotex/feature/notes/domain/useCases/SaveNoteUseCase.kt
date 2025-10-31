package com.personal.madNotex.feature.notes.domain.useCases

import com.personal.madNotex.core.localization.Strings
import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.feature.notes.data.repository.NoteRepository

class SaveNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Result<Long> {
        return try {
            if (note.title.isBlank()) {
                Result.failure(ValidationException(Strings.TITLE_CANNOT_BE_EMPTY))
            } else if (note.body.isBlank()) {
                Result.failure(ValidationException(Strings.BODY_CANNOT_BE_EMPTY))
            } else {
                val noteId = repository.insertNote(note)
                Result.success(noteId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class ValidationException(message: String) : Exception(message)


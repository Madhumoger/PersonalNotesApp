package com.personal.madNotex.feature.notes.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.madNotex.core.localization.Strings
import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.feature.notes.domain.useCases.SaveNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AddNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AddNoteViewState())
    val viewState: StateFlow<AddNoteViewState> = _viewState.asStateFlow()

    fun updateTitle(title: String) {
        _viewState.value = _viewState.value.copy(title = title)
    }

    fun updateBody(body: String) {
        _viewState.value = _viewState.value.copy(body = body)
    }

    fun updateCreatedDate(timestamp: Long) {
        _viewState.value = _viewState.value.copy(createdDateMillis = timestamp)
    }

    fun saveNote(onSuccess: (Long) -> Unit, onError: (String) -> Unit) {
        val currentState = _viewState.value
        val note = Note(
            title = currentState.title,
            body = currentState.body,
            createdDate = Instant.fromEpochMilliseconds(currentState.createdDateMillis)
        )

        viewModelScope.launch {
            saveNoteUseCase(note)
                .onSuccess { noteId -> onSuccess(noteId) }
                .onFailure { error -> onError(error.message ?: Strings.FAILED_TO_SAVE_NOTE) }
        }
    }
}

data class AddNoteViewState(
    val title: String = "",
    val body: String = "",
    val createdDateMillis: Long = Clock.System.now().toEpochMilliseconds()
)


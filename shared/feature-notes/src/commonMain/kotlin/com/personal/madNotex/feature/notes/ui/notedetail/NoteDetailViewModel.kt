package com.personal.madNotex.feature.notes.ui.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.madNotex.core.localization.Strings
import com.personal.madNotex.feature.notes.domain.model.Note
import com.personal.madNotex.feature.notes.domain.useCases.GetNoteByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<NoteDetailViewState>(NoteDetailViewState.Loading)
    val viewState: StateFlow<NoteDetailViewState> = _viewState.asStateFlow()

    fun loadNote(noteId: Long) {
        viewModelScope.launch {
            _viewState.value = NoteDetailViewState.Loading
            val result = getNoteByIdUseCase(noteId)
            result.onSuccess { note ->
                _viewState.value = if (note != null) {
                    NoteDetailViewState.Success(note)
                } else {
                    NoteDetailViewState.Error(Strings.NOTE_NOT_FOUND)
                }
            }.onFailure { error ->
                _viewState.value = NoteDetailViewState.Error(error.message ?: Strings.FAILED_TO_LOAD_NOTE)
            }
        }
    }
}

sealed class NoteDetailViewState {
    data object Loading : NoteDetailViewState()
    data class Success(val note: Note) : NoteDetailViewState()
    data class Error(val message: String) : NoteDetailViewState()
}

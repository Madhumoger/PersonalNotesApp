package com.personal.notes.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NotesJourney(val screenName: String) {

    @Serializable
    data object NoteList : NotesJourney(screenName = "note_list")

    @Serializable
    data object AddNote : NotesJourney(screenName = "add_note")

    @Serializable
    data object PdfViewer : NotesJourney(screenName = "pdf_viewer")

    @Serializable
    data class NoteDetail(val noteId: Long) : NotesJourney(screenName = "note_detail")
}

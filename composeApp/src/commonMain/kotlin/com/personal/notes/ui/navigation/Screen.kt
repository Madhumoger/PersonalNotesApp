package com.personal.notes.ui.navigation

/**
 * Navigation destinations for the app
 */
sealed class Screen(val route: String) {
    data object NoteList : Screen("note_list")
    
    data object AddNote : Screen("add_note")
    
    data object PdfViewer : Screen("pdf_viewer")
    
    data class NoteDetail(val noteId: Long) : Screen("note_detail/{noteId}") {
        companion object {
            const val ROUTE = "note_detail/{noteId}"
            
            fun createRoute(noteId: Long): String = "note_detail/$noteId"
        }
    }
}

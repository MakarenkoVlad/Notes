package vlad.makarenko.notes.uimodel

import androidx.compose.runtime.Immutable
import vlad.makarenko.notes.base.UiEvent
import vlad.makarenko.notes.base.UiState
import vlad.makarenko.notes.data.Note

@Immutable
data class EditNoteScreenState(
    val note: Note,
    val isSaved: Boolean
) : UiState {
    companion object {
        fun initial() = EditNoteScreenState(
            note = Note.empty(),
            isSaved = false,
        )
    }
}

sealed class EditNoteScreenEvent : UiEvent {
    data class ChangeNote(val note: Note) : EditNoteScreenEvent()
    object NoteSaved : EditNoteScreenEvent()
}
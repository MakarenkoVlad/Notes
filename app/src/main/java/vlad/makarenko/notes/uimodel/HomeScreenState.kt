package vlad.makarenko.notes.uimodel

import androidx.compose.runtime.Immutable
import vlad.makarenko.notes.base.UiEvent
import vlad.makarenko.notes.base.UiState
import vlad.makarenko.notes.data.Note

@Immutable
data class HomeScreenUiState(
    val isLoading: Boolean,
    val data: List<Note>,
    val isShowErrorDialog: Boolean,
) : UiState {

    companion object {
        fun initial() = HomeScreenUiState(
            isLoading = true,
            data = emptyList(),
            isShowErrorDialog = false,
        )
    }
}

sealed class HomeScreenUiEvent : UiEvent {

    data class ShowNotes(val data: List<Note>) : HomeScreenUiEvent()

    data class DeleteNote(val note: Note) : HomeScreenUiEvent()

    data class Error(val t: Throwable) : HomeScreenUiEvent()

    object DismissErrorDialog : HomeScreenUiEvent()
}
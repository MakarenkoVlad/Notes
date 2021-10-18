package vlad.makarenko.notes.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import vlad.makarenko.notes.base.BaseViewModel
import vlad.makarenko.notes.base.Reducer
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.repo.NotesRepository
import vlad.makarenko.notes.uimodel.EditNoteScreenEvent
import vlad.makarenko.notes.uimodel.EditNoteScreenState
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val repository: NotesRepository): BaseViewModel<EditNoteScreenState, EditNoteScreenEvent>() {

    private val reducer = EditNoteReducer(EditNoteScreenState.initial())

    override val state: StateFlow<EditNoteScreenState>
        get() = reducer.state

    fun getNote(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val note = repository.getNoteById(id)
        changeNote(note)
    }

    fun changeNote(note: Note) {
        reducer.sendEvent(EditNoteScreenEvent.ChangeNote(note))
    }

    fun saveNote() = viewModelScope.launch {
        repository.saveNote(state.value.note)
        reducer.sendEvent(EditNoteScreenEvent.NoteSaved)
    }

    private class EditNoteReducer(initialState: EditNoteScreenState) : Reducer<EditNoteScreenState, EditNoteScreenEvent>(initialState) {
        override fun reduce(oldState: EditNoteScreenState, event: EditNoteScreenEvent) {
            when(event) {
                is EditNoteScreenEvent.ChangeNote -> {
                    setState(oldState.run { copy(note = event.note) })
                }
                EditNoteScreenEvent.NoteSaved -> {
                    setState(oldState.copy(isSaved = true))
                }
            }
        }
    }
}
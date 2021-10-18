package vlad.makarenko.notes.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vlad.makarenko.notes.base.BaseViewModel
import vlad.makarenko.notes.base.Reducer
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.repo.NotesRepository
import vlad.makarenko.notes.uimodel.HomeScreenUiEvent
import vlad.makarenko.notes.uimodel.HomeScreenUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: NotesRepository) :
    BaseViewModel<HomeScreenUiState, HomeScreenUiEvent>() {

    override val reducer = object : Reducer<HomeScreenUiState, HomeScreenUiEvent>(HomeScreenUiState.initial()) {
        override fun reduce(oldState: HomeScreenUiState, event: HomeScreenUiEvent) {
            when (event) {
                is HomeScreenUiEvent.DeleteNote -> {
                    setState(oldState.run { copy(data = data - event.note) })
                }
                is HomeScreenUiEvent.ShowNotes -> {
                    setState(oldState.copy(data = event.data, isLoading = false))
                }
                is HomeScreenUiEvent.Error -> {
                    setState(oldState.copy(isShowErrorDialog = true))
                }
                HomeScreenUiEvent.DismissErrorDialog -> {
                    setState(oldState.copy(isShowErrorDialog = false))
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            repository.getNotes().collectLatest {
                reducer.sendEvent(HomeScreenUiEvent.ShowNotes(data = it))
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            repository.deleteNote(note.id)
        }.onSuccess {
            reducer.sendEvent(HomeScreenUiEvent.DeleteNote(note = note))
        }.onFailure {
            reducer.sendEvent(HomeScreenUiEvent.Error(it))
        }
    }

    fun dismissErrorDialog() {
        reducer.sendEvent(HomeScreenUiEvent.DismissErrorDialog)
    }
}
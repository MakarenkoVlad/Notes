package vlad.makarenko.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.repo.NotesRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: NotesRepository): ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    init {
        viewModelScope.launch {
            repository.getNotes().collect {
                _notes.postValue(it)
            }
        }
    }
}
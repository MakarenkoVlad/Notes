package vlad.makarenko.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.repo.NotesRepository
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val repository: NotesRepository): ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    fun getNote(id: Int) = viewModelScope.launch {
        _note.postValue(repository.getNoteById(id))
    }

    fun editNote(note: Note) {
        _note.postValue(note)
    }

    fun saveNote() = viewModelScope.launch {
        _note.value?.let {
            repository.updateNote(it)
        }
    }
}
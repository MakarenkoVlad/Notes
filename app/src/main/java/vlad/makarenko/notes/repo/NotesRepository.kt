package vlad.makarenko.notes.repo

import kotlinx.coroutines.flow.Flow
import vlad.makarenko.notes.data.Note

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note

    suspend fun saveNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(id: Int)
}
package vlad.makarenko.notes.repo

import kotlinx.coroutines.flow.Flow
import vlad.makarenko.notes.data.Note
import vlad.makarenko.notes.repo.local.NotesDao
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(private val notesDao: NotesDao): NotesRepository {

    override fun getNotes(): Flow<List<Note>> = notesDao.getNotes()

    override suspend fun getNoteById(id: Int): Note = notesDao.getNoteById(id)

    override suspend fun saveNote(note: Note) = notesDao.insertNote(note)

    override suspend fun updateNote(note: Note) = notesDao.updateNote(note)

    override suspend fun deleteNote(id: Int) = notesDao.deleteNoteById(id)
}
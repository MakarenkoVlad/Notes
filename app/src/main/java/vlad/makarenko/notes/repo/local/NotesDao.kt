package vlad.makarenko.notes.repo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vlad.makarenko.notes.data.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM notes WHERE :id = id")
    suspend fun deleteNoteById(id: Int)

    @Query("SELECT * FROM notes WHERE :id = id")
    suspend fun getNoteById(id: Int): Note
}
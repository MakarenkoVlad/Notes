package vlad.makarenko.notes.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import vlad.makarenko.notes.data.Note

@Database(entities = [Note::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun notesDao() : NotesDao
}
package vlad.makarenko.notes.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vlad.makarenko.notes.repo.NotesRepository
import vlad.makarenko.notes.repo.NotesRepositoryImpl
import vlad.makarenko.notes.repo.local.MainDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class Providers {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context, MainDatabase::class.java, "MainDatabase.db"
        ).build()

        @Provides
        fun provideNotesDao(database: MainDatabase) = database.notesDao()
    }

    @Singleton
    @Binds
    abstract fun bindNotesRepository(repository: NotesRepositoryImpl): NotesRepository
}
package com.vorue.notekmm.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.vorue.notekmm.data.SQLDeLightNoteDataSource
import com.vorue.notekmm.data.local.DatabaseDriverFactory
import com.vorue.notekmm.database.NoteDatabase
import com.vorue.notekmm.domain.NoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SQLDeLightNoteDataSource(NoteDatabase(driver))
    }
}
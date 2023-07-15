package com.vorue.notekmm.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.vorue.notekmm.database.NoteDatabase

actual class DatabaseDriverFactory(private val context : Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            NoteDatabase.Schema, context, "notes.db")
    }
}
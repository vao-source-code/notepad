package com.vorue.notekmm.data

import com.vorue.notekmm.database.NoteDatabase
import com.vorue.notekmm.domain.Note
import com.vorue.notekmm.domain.NoteDataSource
import com.vorue.notekmm.library.DateTimeUtil

class SQLDeLightNoteDataSource(db: NoteDatabase) : NoteDataSource {

    private val noteQueries = db.noteQueries
    override suspend fun insertNote(note: Note) {
        noteQueries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMilli(note.created),
            start = DateTimeUtil.toEpochMilli(note.start),
            end = DateTimeUtil.toEpochMilli(note.end)

        )
    }

    override suspend fun updateNote(note: Note) {
        return noteQueries.updateNote(
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            start = DateTimeUtil.toEpochMilli(note.start),
            end = DateTimeUtil.toEpochMilli(note.end),
            id = note.id!!
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteQueries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }


    override suspend fun deleteNoteById(id: Long) {
        noteQueries.deleteNote(id)
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteQueries.getAllNotes().executeAsList().map { it.toNote() }
    }
}
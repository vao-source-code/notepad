package com.vorue.notekmm.domain

interface NoteDataSource {
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNoteById(id: Long)
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>

}
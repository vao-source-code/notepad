package com.vorue.notekmm.data

import com.vorue.notekmm.domain.Note
import database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

//La función toNote() asigna los valores de las propiedades del objeto NoteEntity a las propiedades correspondientes del objeto Note.
fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    content = content,
    colorHex = colorHex,
    created = Instant.fromEpochMilliseconds(created).toLocalDateTime(TimeZone.currentSystemDefault()),
    start = Instant.fromEpochMilliseconds(start).toLocalDateTime(TimeZone.currentSystemDefault()),
    end = Instant.fromEpochMilliseconds(end).toLocalDateTime(TimeZone.currentSystemDefault())

)

package com.vorue.notekmm.android.note_list

import com.vorue.notekmm.domain.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false,

    )
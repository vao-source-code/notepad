package com.vorue.notekmm.domain

import com.vorue.notekmm.library.DateTimeUtil

class SearchNotes {

    fun execute(notes : List<Note>, query: String): List<Note> {
      if (query.isBlank()) {
        return notes
      }
        return notes.filter { it.title.trim().contains(query, ignoreCase = true) }.sortedBy {
            DateTimeUtil.toEpochMilli(it.created)
        }
    }
}
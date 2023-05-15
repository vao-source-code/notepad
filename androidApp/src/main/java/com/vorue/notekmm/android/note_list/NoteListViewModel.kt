package com.vorue.notekmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorue.notekmm.domain.Note
import com.vorue.notekmm.domain.NoteDataSource
import com.vorue.notekmm.domain.SearchNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()
    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(notes, searchText , isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = notes,
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(  viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState() )

    fun loadNotes(){
        viewModelScope.launch {
           savedStateHandle["notes"] = noteDataSource.getAllNotes()

        }
    }


}
package com.vorue.notekmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorue.notekmm.domain.Note
import com.vorue.notekmm.domain.NoteDataSource
import com.vorue.notekmm.library.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleTextFocused =
        savedStateHandle.getStateFlow("isNoteTitleTextFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentTextFocused =
        savedStateHandle.getStateFlow("isNoteContentTextFocused", false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor", Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleTextFocused,
        noteContent,
        isNoteContentTextFocused,
        noteColor
    ) { noteTitle, isNoteTitleTextFocused, noteContent, isNoteContentTextFocused, noteColor ->
        NoteDetailState(
            noteTitle = noteTitle,
            isNoteTitleHintVisible = noteTitle.isEmpty() &&  !isNoteTitleTextFocused,
            noteContent = noteContent,
            isNoteContentHintVisible = noteContent.isEmpty() && !isNoteContentTextFocused,
            noteColor = noteColor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null


    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->

            if (existingNoteId != -1L) {
                return@let //Si no es -1L, no hago nada deberia abrir la nota
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }

    }

    fun onNoteTitleChanged(noteTitle: String) {
        savedStateHandle["noteTitle"] = noteTitle
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentChanged(noteContent: String) {
        savedStateHandle["noteContent"] = noteContent
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isFocused
    }

    fun saveNote(){
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }



}
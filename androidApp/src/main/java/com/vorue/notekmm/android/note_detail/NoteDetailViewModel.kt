package com.vorue.notekmm.android.note_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vorue.notekmm.domain.Note
import com.vorue.notekmm.domain.NoteDataSource
import com.vorue.notekmm.library.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.experimental.ExperimentalTypeInference

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false)
    private val noteColor = savedStateHandle.getStateFlow(
        "noteColor",
        Note.generateRandomColor()
    )
    private val noteDateInit = savedStateHandle.getStateFlow(
        "noteDateInit",

        DateTimeUtil.toEpochMilli(DateTimeUtil.now())
    )
    private val noteDateEnd = savedStateHandle.getStateFlow(
        "noteDateEnd",
        DateTimeUtil.toEpochMilli(DateTimeUtil.now())
    )

    val state = combine(
        noteTitle,
        noteContent,
        noteDateInit,
        noteDateEnd,
        noteColor)
    { title, content, init, end , col ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isNoteTitleFocused.value,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isNoteContentFocused.value,
            noteColor = col,
            noteDateInit = init,
            noteDateEnd = end,
        )
    }    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                    savedStateHandle["noteDateInit"] = DateTimeUtil.toEpochMilli(note.start)
                    savedStateHandle["noteDateEnd"] = DateTimeUtil.toEpochMilli(note.end)
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle["noteContent"] = text
    }

    fun onNoteColorChanged(color: Long) {
        savedStateHandle["noteColor"] = color
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isFocused
    }

    fun onNoteDateInitChanged(date: Long) {
        savedStateHandle["noteDateInit"] = date
    }

    fun onNoteDateInitChanged(date: LocalDateTime) {
        savedStateHandle["noteDateInit"] = DateTimeUtil.toEpochMilli(date.toKotlinLocalDateTime())

        Log.d("TAG", noteDateInit.value.toString())
    }


    fun onNoteDateEndChanged(date: LocalDateTime) {
        savedStateHandle["noteDateEnd"] = DateTimeUtil.toEpochMilli(date.toKotlinLocalDateTime())
    }
    fun onNoteDateEndChanged(date: Long) {
        savedStateHandle["noteDateEnd"] = date
    }



    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now(),
                    //TODO modificar esto urgente
                    start  = DateTimeUtil.toLocalDateTime(noteDateInit.value),
                    end = DateTimeUtil.toLocalDateTime(noteDateEnd.value)
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }




}
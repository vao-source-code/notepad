package com.vorue.notekmm.domain

import com.vorue.notekmm.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
){
    companion object {
        private val colors = listOf(COLOR_BLUE, COLOR_GREEN, COLOR_ORANGE , COLOR_PINK , COLOR_RED)

        fun generateRandomColor()  = colors.random()
    }
}

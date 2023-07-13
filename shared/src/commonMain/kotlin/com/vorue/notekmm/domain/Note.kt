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
        val colors = listOf(   COLOR_BLUE,
            COLOR_GREEN,
            COLOR_PINK,
            COLOR_PURPLE,
            COLOR_RED,
            COLOR_ORANGE,
            COLOR_YELLOW)

        val nameColor = listOf("COLOR_BLUE",
            "COLOR_GREEN",
            "COLOR_PINK",
            "COLOR_PURPLE",
            "COLOR_RED",
            "COLOR_ORANGE",
            "COLOR_YELLOW")
        fun generateRandomColor()  = colors.random()
        fun nameColor( id : Int ): String{
            return nameColor[id]
        }
    }
}

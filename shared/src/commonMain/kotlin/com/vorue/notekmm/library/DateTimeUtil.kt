package com.vorue.notekmm.library

import kotlinx.datetime.*

object DateTimeUtil {

    //para obtener la hora actual del sistema y lo convierte a la zona horaria actual del sistema
    fun now(): LocalDateTime {
       return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    //convierte un objeto LocalDateTime en un valor de tiempo en milisegundos desde el 1 de enero de 1970
    fun toEpochMilli(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }



    fun formatSample(localDateTime: LocalDateTime): String {
        return localDateTime.toString().replace("T", " ")
    }

    fun formatDate(localDateTime: LocalDateTime): String {
       val month = localDateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if(localDateTime.dayOfMonth < 10) "0${localDateTime.dayOfMonth}" else "${localDateTime.dayOfMonth}"
        val year = localDateTime.year

        val hour = if(localDateTime.hour < 10) "0${localDateTime.hour}" else "${localDateTime.hour}"
        val minute = if(localDateTime.minute < 10) "0${localDateTime.minute}" else "${localDateTime.minute}"

        return buildString {
            append("$day $month $year , $hour:$minute")
        }
    }

    fun formatDate(localDateTime: Long): String {
        return formatDate(toLocalDateTime(localDateTime))
    }


    fun toLocalDateTime(value: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(value).toLocalDateTime(TimeZone.currentSystemDefault())
    }
}
package com.vorue.notekmm.android.note_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vorue.notekmm.domain.Note
import com.vorue.notekmm.presentation.COLOR_BLUE
import com.vorue.notekmm.presentation.COLOR_GREEN
import com.vorue.notekmm.presentation.COLOR_ORANGE
import com.vorue.notekmm.presentation.COLOR_PINK
import com.vorue.notekmm.presentation.COLOR_PURPLE
import com.vorue.notekmm.presentation.COLOR_RED
import com.vorue.notekmm.presentation.COLOR_YELLOW

val listColor = Note.colors
@Composable
fun LazyVerticalColorButton(modifier: Modifier, viewModel: NoteDetailViewModel) {

    LazyVerticalGrid(modifier = Modifier.width(210.dp), columns = GridCells.Fixed(7), content = {
        items(listColor) { it ->
            val i = listColor.indexOf(it)
            Button(modifier = Modifier
                .height(40.dp)
                .width(30.dp)
                .padding(5.dp),
                onClick = { viewModel.onNoteColorChanged(it) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(it)),
                border = BorderStroke(1.dp, Color.LightGray)) {
                // Text(text = Note.nameColor(i))
            }
        }
    })

}
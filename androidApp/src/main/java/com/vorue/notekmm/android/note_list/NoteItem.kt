package com.vorue.notekmm.android.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vorue.notekmm.domain.Note
import com.vorue.notekmm.library.DateTimeUtil
import com.vorue.notekmm.presentation.COLOR_PINK


@Composable
fun NoteItem(
    note: Note,
    backgroundColor: Color,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier
) {

    val formattedDate = remember(note.created) {
        DateTimeUtil.formatDate(note.created)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable { onNoteClick() }
            .padding(16.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = note.title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete Note",
                modifier = Modifier.clickable(MutableInteractionSource(), null) { onDeleteClick() }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = note.content, fontWeight = FontWeight.Light, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = formattedDate, color = Color.DarkGray , fontWeight = FontWeight.Light, fontSize = 12.sp , modifier = Modifier.align(Alignment.End))



    }
}


@Preview
@Composable
fun NoteItemPreview() {
    NoteItem(
        note = Note(
            id = 1,
            title = "Title",
            content = "Content",
            colorHex = COLOR_PINK,
            created = DateTimeUtil.now(),
            start = DateTimeUtil.now(),
            end = DateTimeUtil.now()
        ),
        backgroundColor = Color.White,
        onNoteClick = {},
        onDeleteClick = {},
        modifier = Modifier
    )
}


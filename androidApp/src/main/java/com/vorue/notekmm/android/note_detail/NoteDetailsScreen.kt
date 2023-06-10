package com.vorue.notekmm.android.note_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vorue.notekmm.android.note_list.HideableSearchTextField
import com.vorue.notekmm.android.note_list.NoteItem


@Composable
fun NoteDetailsScreen(
    navController: NavController,
    noteId: Long,
    viewModel: NoteDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved)
            navController.popBackStack()
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = viewModel::saveNote, backgroundColor = Color.Black) {
            Icon(
                imageVector = Icons.Default.Check,
                tint = Color.White,
                contentDescription = "Save Note"
            )
        }
    }) { paddingValues ->

        NoteDetailsScreenBody(
            state = state,
            modifier = Modifier,
            paddingValues = paddingValues,
            viewModel = viewModel
        )



    }

}

@Composable
fun NoteDetailsScreenBody(state: NoteDetailState, modifier: Modifier, paddingValues: PaddingValues, viewModel: NoteDetailViewModel) {
    Column(

        modifier = Modifier
            .background(Color(state.noteColor))
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {

        TransparentHintTextField(
            text = state.noteTitle,
            hint = "Enter a title...",
            isHintVisible = state.isNoteTitleHintVisible,
            onValueChanged = viewModel::onNoteTitleChanged,
            onFocusChanged = {
                viewModel.onNoteTitleFocusChanged(it.isFocused)
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TransparentHintTextField(
            text = state.noteContent,
            hint = "Enter some content...",
            isHintVisible = state.isNoteContentHintVisible,
            onValueChanged = viewModel::onNoteContentChanged,
            onFocusChanged = {
                viewModel.onNoteContentFocusChanged(it.isFocused)
            },
            singleLine = false,
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier.weight(1f)
        )
    }

}

@Preview
@Composable
fun PreviewNoteDetailsScreen() {
    NoteDetailsScreenBody(
        state = NoteDetailState(),
        modifier = Modifier,
        paddingValues = PaddingValues(),
        viewModel = hiltViewModel()
    )
}
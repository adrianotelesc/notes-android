package com.adrianotelesc.notes.ui.screen.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.adrianotelesc.notes.R
import com.adrianotelesc.notes.data.model.Note
import com.adrianotelesc.notes.ui.theme.NotesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(viewModel: NotesViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Content(
        notes = uiState.notes,
        onAddClick = viewModel::addNote,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
)
@Composable
fun Content(
    notes: List<Note> = emptyList(),
    onAddClick: () -> Unit = {},
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = appBarState)

    val listState = rememberLazyStaggeredGridState()

    val showButton by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction == 0f }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes") },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showButton,
                enter = scaleIn(
                    animationSpec = keyframes {
                        durationMillis = 120
                    }
                ),
                exit = scaleOut(
                    animationSpec = keyframes {
                        durationMillis = 120
                    }
                ),
            ) {
                FloatingActionButton(
                    onClick = {
                        onAddClick()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        },
    ) { padding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            state = listState,
        ) {
            items(notes) { note ->
                Card {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = note.text,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview(
    @PreviewParameter(NotePreviewParameterProvider::class) notes: List<Note>,
) {
    NotesTheme {
        Content(notes = notes)
    }
}

class NotePreviewParameterProvider : PreviewParameterProvider<List<Note>> {
    override val values = sequenceOf(
        listOf(
            Note(text = "My shopping list\n- Eggs\n- Rice\n- Beans\n- Eggs\n- Grapes"),
            Note(text = "My favorite person's birthday is July 23"),
            Note(text = "My watchlist\n- Chainsaw Man 😳\n- Jujutsu Kaisen 👻\n- One Piece 🏴‍☠️"),
        )
    )
}

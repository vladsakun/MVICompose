package com.example.mvicompose.ui.feature.charactersList.composables

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvicompose.R
import com.example.mvicompose.ui.base.SIDE_EFFECTS_KEY
import com.example.mvicompose.ui.common.NetworkError
import com.example.mvicompose.ui.common.Progress
import com.example.mvicompose.ui.feature.charactersList.CharactersListContract
import com.example.mvicompose.ui.feature.movie.list.composables.MVIComposeAppTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onEach

@Composable
fun CharactersListScreen(
    state: CharactersListContract.State,
    effectFlow: Flow<CharactersListContract.Effect>,
    onEventSent: (event: CharactersListContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: CharactersListContract.Effect.Navigation) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is CharactersListContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MVIComposeAppTopBar(text = stringResource(id = R.string.characters)) }
    ) {
        when {
            state.isLoading -> Progress()
            state.isError -> NetworkError { onEventSent(CharactersListContract.Event.Retry) }
            else -> CharactersList(characters = state.characters) { characterId ->
                onEventSent(CharactersListContract.Event.CharacterItemClicked(characterId))
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewCharactersListScreen() {
    CharactersListScreen(CharactersListContract.State.getDefaultState(), emptyFlow(), {}, {})
}
package com.example.mvicompose.ui.feature.charactersList.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvicompose.ui.base.SIDE_EFFECTS_KEY
import com.example.mvicompose.ui.common.NetworkError
import com.example.mvicompose.ui.common.Progress
import com.example.mvicompose.ui.feature.charactersList.CharactersListContract
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

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is CharactersListContract.Effect.Navigation -> {
                    onNavigationRequested.invoke(effect)
                }
            }
        }.collect()
    }

    when {
        state.isLoading -> Progress()
        state.isError -> NetworkError { onEventSent.invoke(CharactersListContract.Event.Retry) }
        else -> CharactersList(characters = state.characters) { characterId ->
            onEventSent.invoke(CharactersListContract.Event.CharacterItemClicked(characterId))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharactersListScreen() {
    CharactersListScreen(CharactersListContract.State.getDefaultState(), emptyFlow(), {}, {})
}
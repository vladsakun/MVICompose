package com.example.mvicompose.ui.feature.character.composables

import androidx.compose.runtime.Composable
import com.example.mvicompose.ui.common.NetworkError
import com.example.mvicompose.ui.common.Progress
import com.example.mvicompose.ui.feature.character.CharacterDetailsContract
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterDetailsScreen(
    state: CharacterDetailsContract.State,
    effectFlow: Flow<CharacterDetailsContract.Effect>,
    onEventSent: (event: CharacterDetailsContract.Event) -> Unit
) {
    when {
        state.isLoading -> Progress()
        state.isError -> NetworkError { onEventSent.invoke(CharacterDetailsContract.Event.Retry) }
        else -> CharacterDetails(character = state.character)
    }
}
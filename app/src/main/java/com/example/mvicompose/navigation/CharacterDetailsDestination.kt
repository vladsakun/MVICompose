package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mvicompose.ui.feature.character.CharacterDetailsViewModel
import com.example.mvicompose.ui.feature.character.composables.CharacterDetailsScreen

@Composable
fun CharacterDestination(navController: NavController, characterId: String) {
    val viewModel: CharacterDetailsViewModel = viewModel()
    viewModel.loadCharacter(characterId)

    CharacterDetailsScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { viewModel.setEvent(it) }
    )
}
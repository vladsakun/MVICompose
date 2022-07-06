package com.example.mvicompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mvicompose.ui.feature.charactersList.CharactersListContract
import com.example.mvicompose.ui.feature.charactersList.CharactersListViewModel
import com.example.mvicompose.ui.feature.charactersList.composables.CharactersListScreen

@Composable
fun CharactersScreenDestination(navController: NavController) {
    val viewModel: CharactersListViewModel = viewModel()

    CharactersListScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is CharactersListContract.Effect.Navigation.ToCharacterDetails -> {
                    navController.navigateToCharacterDetails(navigationEffect.characterId)
                }
            }
        }
    )
}
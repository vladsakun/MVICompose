package com.example.mvicompose.ui.feature.charactersList

import com.example.mvicompose.CharactersListQuery
import com.example.mvicompose.ui.base.ViewEvent
import com.example.mvicompose.ui.base.ViewSideEffect
import com.example.mvicompose.ui.base.ViewState

class CharactersListContract {

    sealed class Event : ViewEvent {
        object Retry : Event()
        class CharacterItemClicked(val characterId: String) : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isError: Boolean,
        val characters: List<CharactersListQuery.Result?>
    ) : ViewState {

        companion object {
            fun getDefaultState() = State(
                isLoading = true,
                isError = false,
                characters = emptyList()
            )
        }
    }

    sealed class Effect : ViewSideEffect {

        sealed class Navigation : Effect() {
            class ToCharacterDetails(val characterId: String) : Navigation()
        }
    }
}
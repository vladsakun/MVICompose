package com.example.mvicompose.ui.feature.character

import com.example.mvicompose.CharacterQuery
import com.example.mvicompose.data.repository.getMockedCharacter
import com.example.mvicompose.ui.base.ViewEvent
import com.example.mvicompose.ui.base.ViewSideEffect
import com.example.mvicompose.ui.base.ViewState

class CharacterDetailsContract {

    sealed class Event : ViewEvent {
        object Retry : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isError: Boolean,
        val character: CharacterQuery.Character
    ) : ViewState {

        companion object {
            fun getDefaultState() = State(
                isLoading = true,
                isError = false,
                character = getMockedCharacter()
            )
        }
    }

    sealed class Effect : ViewSideEffect
}
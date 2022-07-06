package com.example.mvicompose.ui.feature.charactersList

import androidx.lifecycle.viewModelScope
import com.example.mvicompose.data.repository.MainRepositoryImpl
import com.example.mvicompose.ui.base.BaseViewModel
import com.example.mvicompose.ui.feature.charactersList.CharactersListContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersListViewModel : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State.getDefaultState()

    init {
        getCharacters()
    }

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.Retry -> getCharacters()
            is Event.CharacterItemClicked -> setEffect {
                Effect.Navigation.ToCharacterDetails(event.characterId)
            }
        }
    }

    private fun getCharacters() {
        setState { copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                MainRepositoryImpl.getCharacters()
            }.onSuccess {
                it.data?.characters?.results?.let { characters ->
                    setState { copy(isLoading = false, characters = characters) }
                } ?: run {
                    setState { copy(isLoading = false, isError = true) }
                }
            }.onFailure {
                setState { copy(isLoading = false, isError = true) }
            }
        }
    }
}
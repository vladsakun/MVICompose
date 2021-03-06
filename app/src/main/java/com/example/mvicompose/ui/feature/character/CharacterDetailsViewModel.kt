package com.example.mvicompose.ui.feature.character

import androidx.lifecycle.viewModelScope
import com.example.mvicompose.data.repository.MainRepositoryImpl
import com.example.mvicompose.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsViewModel :
    BaseViewModel<CharacterDetailsContract.Event, CharacterDetailsContract.State, CharacterDetailsContract.Effect>() {

    private var characterId: String? = null

    override fun setInitialState() = CharacterDetailsContract.State.getDefaultState()
    override fun handleEvents(event: CharacterDetailsContract.Event) {
        when (event) {
            CharacterDetailsContract.Event.Retry -> getCharacter(characterId.orEmpty())
        }
    }

    fun loadCharacter(characterId: String) {
        this.characterId = characterId.lowercase()
        getCharacter(characterId)
    }

    private fun getCharacter(id: String) {
        setState { copy(isLoading = true, isError = false) }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                MainRepositoryImpl.getCharacter(id)
            }.onSuccess {
                it.data?.character?.let { character ->
                    setState { copy(isLoading = false, character = character) }
                } ?: run {
                    setState { copy(isLoading = false, isError = true) }
                }
            }.onFailure {
                setState { copy(isLoading = false, isError = true) }
            }
        }
    }
}
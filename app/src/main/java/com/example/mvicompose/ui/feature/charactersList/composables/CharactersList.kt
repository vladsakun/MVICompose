package com.example.mvicompose.ui.feature.charactersList.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvicompose.CharactersListQuery
import com.example.mvicompose.data.repository.getMockedCharactersList

@Composable
fun CharactersList(
    characters: List<CharactersListQuery.Result?>,
    onItemClick: (characterId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
    ) {
        items(characters) { character ->
            if (character != null) {
                CharactersListItem(character = character, onItemClick = onItemClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCharactersList() {
    CharactersList(characters = getMockedCharactersList(), {})
}
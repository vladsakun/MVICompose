package com.example.mvicompose.ui.feature.charactersList.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mvicompose.CharactersListQuery
import com.example.mvicompose.R
import com.example.mvicompose.data.repository.getMockedCharactersList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharactersListItem(
    character: CharactersListQuery.Result,
    onItemClick: (characterId: String) -> Unit,
) {
    val isInEditMode = LocalView.current.isInEditMode
    Card(
        shape = RoundedCornerShape(10.dp),
        onClick = { onItemClick(character.id.orEmpty()) }
    ) {
        Row {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                placeholder = if (isInEditMode) painterResource(id = R.drawable.samuel) else null,
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = character.name.orEmpty(),
                    style = MaterialTheme.typography.h6,
                )

                Text(
                    text = stringResource(
                        id = R.string.species,
                        character.species.orEmpty()
                    ),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(2.dp))
}


@Preview(showBackground = true)
@Composable
fun PreviewCharactersListItem() {
    CharactersListItem(character = getMockedCharactersList().first(), {})
}
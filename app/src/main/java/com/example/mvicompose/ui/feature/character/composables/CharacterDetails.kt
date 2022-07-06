package com.example.mvicompose.ui.feature.character.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mvicompose.CharacterQuery
import com.example.mvicompose.R
import com.example.mvicompose.data.repository.getMockedCharacter

@Composable
fun CharacterDetails(character: CharacterQuery.Character) {
    Row(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = character.image,
            placeholder = painterResource(id = R.drawable.samuel),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.onBackground, CircleShape)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(start = 16.dp)
        ) {
            CharacterInfoText(
                text = stringResource(
                    id = R.string.id,
                    character.id.orEmpty()
                ),
            )
            CharacterInfoText(
                text = stringResource(
                    id = R.string.name,
                    character.name.orEmpty()
                ),
            )
            CharacterInfoText(
                text = stringResource(
                    id = R.string.status,
                    character.status.orEmpty()
                ),
            )
            CharacterInfoText(
                text = stringResource(
                    id = R.string.species,
                    character.species.orEmpty()
                ),
            )
            CharacterInfoText(
                text = stringResource(
                    id = R.string.type,
                    character.type.orEmpty()
                ),
            )
            CharacterInfoText(
                text = stringResource(
                    id = R.string.gender,
                    character.gender.orEmpty()
                ),
            )
        }
    }
}

@Composable
fun CharacterInfoText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCharacterDetails() {
    CharacterDetails(character = getMockedCharacter())
}

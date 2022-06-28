package com.example.mvicompose.ui.feature.movie.list.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvicompose.R

@Composable
fun MoviesTopBar(
    text: String = stringResource(id = R.string.movies_screen_top_bar_title)
) {
    TopAppBar(
        modifier = Modifier.padding(),
        title = { Text(text = text) }
    )
}

@Preview(showBackground = true)
@Composable
fun ReposTopBarPreview() {
    MoviesTopBar()
}
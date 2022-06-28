package com.example.mvicompose.ui.feature.movie.list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvicompose.R
import com.example.mvicompose.ui.theme.OnSurfaceBackgroundAlpha

@Composable
fun MoviesListHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.onSurface.copy(alpha = OnSurfaceBackgroundAlpha))
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ) {
        Text(
            text = stringResource(id = R.string.movies_list_header),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesListHeaderPreview() {
    MoviesListHeader()
}
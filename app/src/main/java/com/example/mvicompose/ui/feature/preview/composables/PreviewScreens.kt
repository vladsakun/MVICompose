package com.example.mvicompose.ui.feature.preview.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mvicompose.R
import com.example.mvicompose.ui.theme.MVIComposeTheme

@Composable
fun PreviewScreen() {
    MVIComposeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.preview),
                style = MaterialTheme.typography.h4
            )
        }
    }
}
package com.example.mvicompose.ui.feature.movie.details.composables

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mvicompose.R
import com.example.mvicompose.cryptography.BiometricPromptUtils
import com.example.mvicompose.ui.base.SIDE_EFFECTS_KEY
import com.example.mvicompose.ui.feature.movie.details.MovieDetailsContract
import com.example.mvicompose.ui.feature.movie.list.composables.MoviesTopBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsContract.State,
    effectFlow: Flow<MovieDetailsContract.Effect>?,
    onEventSent: (event: MovieDetailsContract.Event) -> Unit
) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xxsmall)
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val snackBarMessage = stringResource(id = R.string.success_transaction)

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is MovieDetailsContract.Effect.ShowBiometricPromptForDecryption -> {
                    val biometricPrompt: BiometricPrompt? =
                        BiometricPromptUtils.createBiometricPrompt(context) {
                            onEventSent(
                                MovieDetailsContract.Event.AuthViaBiometricSuccess(
                                    it,
                                    context.applicationContext
                                )
                            )
                        }
                    val promptInfo = BiometricPromptUtils.createPromptInfo(context)
                    biometricPrompt?.authenticate(promptInfo, effect.cryptoObject)
                }
                is MovieDetailsContract.Effect.SuccessTransaction -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackBarMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }?.collect()
    }

    state.movie?.let { movie ->
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { MoviesTopBar(movie.title) },
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier.padding(paddingMedium)
                ) {
                    Image(
                        painter = painterResource(id = movie.posterDrawableResId),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Fit,
                    )

                    Spacer(modifier = Modifier.size(paddingSmall))

                    Column(
                        modifier = Modifier.weight(2f)
                    ) {
                        LabelText(text = stringResource(id = R.string.movie_label_rating))

                        Spacer(modifier = Modifier.size(paddingXXSmall))

                        Text(
                            text = stringResource(
                                id = R.string.movie_rating,
                                movie.voteAverage.toString()
                            ),
                            style = MaterialTheme.typography.subtitle1
                        )

                        Spacer(modifier = Modifier.size(paddingSmall))

                        LabelText(text = stringResource(id = R.string.movie_label_overview))

                        Spacer(modifier = Modifier.size(paddingXXSmall))

                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.size(paddingMedium))

                Text(
                    text = stringResource(id = R.string.movie_label_actors),
                    modifier = Modifier
                        .padding(start = paddingMedium, top = paddingMedium),
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.size(paddingXXSmall))

                ActorsList(actorsDrawableResIds = movie.actorsDrawableResIds)

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = paddingMedium),
                    onClick = {
                        onEventSent(MovieDetailsContract.Event.BuyButtonClicked(context.applicationContext))
                    }
                ) {
                    Text(stringResource(id = R.string.buy))
                }
            }
        }
    }
}

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.subtitle2
    )
}
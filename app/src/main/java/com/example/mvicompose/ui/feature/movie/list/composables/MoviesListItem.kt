package com.example.mvicompose.ui.feature.movie.list.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mvicompose.R
import com.example.mvicompose.data.model.Movie
import com.example.mvicompose.data.model.buildMoviePreview

@Composable
fun MoviesListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xxsmall)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val dividerStartIntent = dimensionResource(id = R.dimen.user_list_item_start_indent)

    val imageWidth = 150.dp

    val primaryColor = MaterialTheme.colors.primary
    val primaryVariantColor = MaterialTheme.colors.primaryVariant

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(movie)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            val (image, rating, title, overview) = createRefs()

            Image(
                painter = painterResource(id = movie.posterDrawableResId),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(imageWidth, 200.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    primaryColor,
                                    primaryVariantColor,
                                )
                            ),
                            radius = this.size.maxDimension
                        )
                    }
                    .constrainAs(rating) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(image.end)
                        start.linkTo(image.end)
                    },
                text = movie.voteAverage.toString(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = movie.title,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(image.end, margin = paddingMedium)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .constrainAs(overview) {
                        start.linkTo(title.start)
                        end.linkTo(parent.end)
                        top.linkTo(title.bottom, margin = paddingXXSmall)
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
    Divider(
        startIndent = dividerStartIntent,
        modifier = Modifier.padding(end = paddingMedium)
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesListItemPreview() {
    MoviesListItem(movie = buildMoviePreview(), onItemClick = {})
}
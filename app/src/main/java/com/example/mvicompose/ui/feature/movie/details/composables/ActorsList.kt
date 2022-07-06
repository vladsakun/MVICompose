package com.example.mvicompose.ui.feature.movie.details.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvicompose.R
import com.example.mvicompose.data.model.buildActorsPreview

@Composable
fun ActorsList(actorsDrawableResIds: List<Int>) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)

    LazyRow {
        itemsIndexed(actorsDrawableResIds) { index, drawable ->
            Image(
                painter = painterResource(id = drawable),
                contentDescription = "",
                Modifier
                    .padding(
                        start = paddingMedium,
                        end = if (index == actorsDrawableResIds.lastIndex) paddingMedium else 0.dp
                    )
                    .width(100.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewActorsList() {
    ActorsList(actorsDrawableResIds = buildActorsPreview())
}
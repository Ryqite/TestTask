package com.example.innowise.Presentation.UI_Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.innowise.Presentation.Models.PhotosItem
import com.example.innowise.R

@Composable
fun PhotosBookmarksGrid(
    photos: List<PhotosItem>,
    modifier: Modifier = Modifier,
    onClick: (String)->Unit,
    state: Boolean
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(photos) { photo ->
            Box {
                AsyncImage(
                    model = photo.image,
                    placeholder = painterResource(R.drawable.image_placeholder_light),
                    error = painterResource(R.drawable.appicon),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable{onClick(photo.title)},
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = photo.author,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                        .background(Color(0xFF0000000).copy(alpha = 0.4f))
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }
        }
    }
}

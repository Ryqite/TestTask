package com.example.innowise.Presentation.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innowise.R
import coil.compose.AsyncImage
import com.example.innowise.Presentation.Models.PhotosItem
import com.example.innowise.Presentation.UI_Components.EmptyDetails
import com.example.innowise.Presentation.Utils.downloadImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    photo: PhotosItem?,
    photos: List<PhotosItem>,
    onBack: () -> Unit,
    state: Boolean,
    onBookmark: (PhotosItem) -> Unit,
    onExplore: () -> Unit
) {
    val context = LocalContext.current
    var scale by remember { mutableStateOf(1f) }
    val transformState = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
        scale = scale.coerceIn(1f, 4f)
    }
    when {
        photo?.image == "" -> EmptyDetails(onExplore = onExplore, onBack = onBack)
        else ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                CenterAlignedTopAppBar(
                    title = {
                        photo?.author?.let {
                            Text(
                                text = it,
                                fontSize = 18.sp,
                            )
                        }
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .background(Color(0xFFF3F5F9), RoundedCornerShape(16.dp))
                                .size(40.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
                if (state) {
                    LinearProgressIndicator(
                        color = Color(0xFFBB1020),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                AsyncImage(
                    model = photo?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .transformable(transformState)
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()

                                    if (event.changes.all { !it.pressed }) {
                                        scale = 1f
                                    }
                                }
                            }
                        },
                    contentScale = ContentScale.Crop,
                    onError = {
                        Toast.makeText(
                            context,
                            "Failed to load image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = {photo?.image?.let {
                            downloadImage(context, it)
                        }},
                        modifier = Modifier.width(180.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF3F5F9)
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Color(0xFFBB1020), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.downloading),
                                    contentDescription = null,
                                    tint = Color.White
                                )

                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Download",
                                fontSize = 14.sp,
                                color = Color(0xFF1E1E1E),
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            if (photo != null && photos.all{it.title != photo.title}) {
                                onBookmark(photo)
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(if (photos.all{it.title != photo?.title}) Color(0xFFBB1020) else Color(0xFFF3F5F9),
                                CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_saving),
                            contentDescription = "Bookmark",
                            tint = if (photos.all{it.title != photo?.title}) Color.White else Color.Black
                        )
                    }
                }
            }
    }
}

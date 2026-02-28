package com.example.innowise.Presentation.Screens

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innowise.R
import coil.compose.AsyncImage
import com.example.innowise.Presentation.Models.PhotosItem
import com.example.innowise.Presentation.UI_Components.EmptyDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    photo: PhotosItem?,
    onBack: () -> Unit,
    onDownload: () -> Unit,
    onBookmark: (PhotosItem) -> Unit,
    onExplore: () -> Unit
) {
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

                Spacer(modifier = Modifier.height(20.dp))

                AsyncImage(
                    model = photo?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = onDownload,
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
                            if (photo != null && !photo.isSaved) {
                                onBookmark(photo)
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(if (photo?.isSaved == true) Color(0xFFF3F5F9) else Color(0xFFBB1020),
                                CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_saving),
                            contentDescription = "Bookmark",
                            tint = if (photo?.isSaved == true) Color.Black else Color.White
                        )
                    }
                }
            }
    }
}
package com.example.innowise.Presentation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innowise.Presentation.Models.BottomTab
import com.example.innowise.Presentation.Models.PhotosItem
import com.example.innowise.Presentation.UI_Components.BookmarksStub
import com.example.innowise.Presentation.UI_Components.BottomNavBar
import com.example.innowise.Presentation.UI_Components.EmptyDetails
import com.example.innowise.Presentation.UI_Components.PhotosBookmarksGrid

@Composable
fun Bookmarks(
    photos: List<PhotosItem>,
    state: Boolean,
    onExplore: () -> Unit,
    onClick: (String)->Unit,
    currentTab: BottomTab,
    onTabSelected: (BottomTab)-> Unit
) {
    when {
        photos.isEmpty() -> BookmarksStub(onExplore = onExplore)
        else ->
            Scaffold(

                bottomBar = {
                    BottomNavBar(currentTab = currentTab, onTabSelected = onTabSelected)
                }

            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Bookmarks",
                        textAlign = TextAlign.Center,
                        fontWeight = Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state) {
                        LinearProgressIndicator(
                            color = Color(0xFFBB1020),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    when {
                        photos.isEmpty() ->
                            BookmarksStub(onExplore)

                        else ->
                            PhotosBookmarksGrid(
                                photos,
                                modifier = Modifier.weight(1f),
                                onClick = onClick
                            )
                    }
                }
            }
    }
}
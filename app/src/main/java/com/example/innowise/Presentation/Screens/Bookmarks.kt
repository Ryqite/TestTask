package com.example.innowise.Presentation.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innowise.Presentation.Models.HomeState
import com.example.innowise.Presentation.UI_Components.BookmarksStub
import com.example.innowise.Presentation.UI_Components.CollectionsRow
import com.example.innowise.Presentation.UI_Components.EmptyStub
import com.example.innowise.Presentation.UI_Components.NetworkStub
import com.example.innowise.Presentation.UI_Components.PhotosBookmarksGrid
import com.example.innowise.Presentation.UI_Components.PhotosGrid
import com.example.innowise.Presentation.UI_Components.SearchBar

@Composable
fun Bookmarks(
    state: HomeState,
    onExplore: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Bookmarks", textAlign = TextAlign.Center, fontWeight = Bold, fontSize = 18.sp, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(12.dp))

        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            state.photos.isEmpty() -> BookmarksStub(onExplore)
            else -> PhotosBookmarksGrid(state.photos, modifier = Modifier.weight(1f))
        }
    }
}

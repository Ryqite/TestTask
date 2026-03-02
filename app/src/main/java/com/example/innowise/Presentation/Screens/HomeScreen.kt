package com.example.innowise.Presentation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.innowise.Presentation.Models.BottomTab
import com.example.innowise.Presentation.Models.PhotosItem
import com.example.innowise.Presentation.UI_Components.BottomNavBar
import com.example.innowise.Presentation.UI_Components.CollectionsRow
import com.example.innowise.Presentation.UI_Components.EmptyStub
import com.example.innowise.Presentation.UI_Components.NetworkStub
import com.example.innowise.Presentation.UI_Components.PhotosGrid
import com.example.innowise.Presentation.UI_Components.SearchBar

@Composable
fun HomeScreen(
    photos: List<PhotosItem>,
    query: String,
    state: Boolean,
    collectionRow: List<String>,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onExplore: () -> Unit,
    selected: String?,
    onSelect: (String) -> Unit,
    onClick: (String)->Unit,
    isNetworkError: Boolean,
    tryAgain: ()->Unit,
    currentTab: BottomTab,
    onTabSelected: (BottomTab)-> Unit
) {

    Scaffold(

        bottomBar = {
            BottomNavBar(currentTab = currentTab, onTabSelected = onTabSelected)
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onClear = onClear
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (collectionRow.isNotEmpty()) {
                CollectionsRow(collectionRow, selected = selected, onSelect = onSelect)
            }

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
                (isNetworkError && photos.isEmpty()) -> {
                    NetworkStub(
                        tryAgain = tryAgain
                    )
                }
                photos.isEmpty() -> {
                    EmptyStub(
                        onExplore = {
                            onExplore()
                        }
                    )
                }
                else -> PhotosGrid(photos, modifier = Modifier.weight(1f), onClick = onClick)
            }
        }
    }
}
package com.example.innowise.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.innowise.Presentation.Models.HomeState
import com.example.innowise.Presentation.Models.PhotosItem
import com.example.innowise.Presentation.Screens.Bookmarks
import com.example.innowise.Presentation.Screens.HomeScreen
import com.example.innowise.Presentation.Screens.PhotoDetailsScreen
import com.example.innowise.Presentation.theme.InnowiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {

            val state = HomeState(
                query = "",
                photos = listOf(
                    PhotosItem(title = "alex himorov", image ="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d"),
                    PhotosItem(title = "zmei gorynych", image ="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d"),
                    PhotosItem(title = "kiril gnomov", image ="https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d")
                ),
                collections = listOf("dog","cat","bird","fish","ocean","mountain","river"),
                isLoading = false
            )
            var selectedCollection by remember { mutableStateOf<String?>(state.collections[0]) }
            InnowiseTheme {
                PhotoDetailsScreen(state.photos[0], onBack = {}, onDownload = {}, onBookmark = {})
//                Bookmarks(state, onExplore = {})
//                HomeScreen(state, onSearch = {}, onClear = {}, onExplore = {},
//                    selected = selectedCollection, onSelect = {selectedCollection = it},
//                    tryAgain = {})
            }
        }
    }
}

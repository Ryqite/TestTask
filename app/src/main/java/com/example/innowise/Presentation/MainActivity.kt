package com.example.innowise.Presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.innowise.Data.DataSource.Local.LocalDataSourceImpl
import com.example.innowise.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.innowise.Data.Repository.PhotosDatabaseRepositoryImpl
import com.example.innowise.Data.Repository.PhotosNetworkRepositoryImpl
import com.example.innowise.Domain.UseCases.DeletePhotoUseCase
import com.example.innowise.Domain.UseCases.GetAllPhotosUseCase
import com.example.innowise.Domain.UseCases.GetPhotosBySearchUseCase
import com.example.innowise.Domain.UseCases.InsertNewPhotoUseCase
import com.example.innowise.Domain.UseCases.UpdatePhotoUseCase
import com.example.innowise.Presentation.Models.BottomTab
import com.example.innowise.Presentation.Screens.Bookmarks
import com.example.innowise.Presentation.Screens.DetailsScreen
import com.example.innowise.Presentation.Screens.HomeScreen
import com.example.innowise.Presentation.ViewModels.DatabaseViewModel
import com.example.innowise.Presentation.ViewModels.NetworkViewModel
import com.example.innowise.Presentation.theme.InnowiseTheme
import com.example.innowise.R
import com.example.week12.Presentation.Utils.NavigationScreens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @Inject
    lateinit var localDataSourceImpl: LocalDataSourceImpl

    @Inject
    lateinit var photosNetworkRepositoryImpl: PhotosNetworkRepositoryImpl

    @Inject
    lateinit var photosDatabaseRepositoryImpl: PhotosDatabaseRepositoryImpl

    @Inject
    lateinit var getBooksBySearchUseCase: GetPhotosBySearchUseCase

    @Inject
    lateinit var insertNewBookUseCase: InsertNewPhotoUseCase

    @Inject
    lateinit var updateBookUseCase: UpdatePhotoUseCase

    @Inject
    lateinit var deleteBookUseCase: DeletePhotoUseCase

    @Inject
    lateinit var getAllBooksUseCase: GetAllPhotosUseCase
    private val networkViewModel: NetworkViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            InnowiseTheme {
                val photos by networkViewModel.photosBySearch.collectAsState()
                val searchQuery by networkViewModel.searchQuery.collectAsState()
                val topKeys by networkViewModel.topKeys.collectAsState()
                val selected by networkViewModel.selected.collectAsState()
                val savedPhotos by databaseViewModel.photosFromDb.collectAsState()
                val savedPhotosIds by databaseViewModel.savedPhotosIds.collectAsState()
                val stateLoading by networkViewModel.stateLoading.collectAsState()
                val navController = rememberNavController()
                var currentTab by remember { mutableStateOf(BottomTab.HOME) }
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.HomeScreen
                ) {
                    composable<NavigationScreens.HomeScreen> {
                        HomeScreen(
                            photos = photos,
                            query = searchQuery,
                            collectionRow = topKeys,
                            onQueryChange = {query ->
                                networkViewModel.onSearchQueryChanged(query)
                            },
                            onClear = {networkViewModel.cancelCollector()},
                            onExplore = {},
                            selected = selected,
                            onSelect = {selectedTab->
                                networkViewModel.onSelectedChanged(selectedTab)
                            },
                            state = stateLoading,
                            tryAgain = {},
                            onClick = {photoId->
                                navController.navigate(NavigationScreens.DetailsScreen(photoId = photoId))},
                            currentTab = currentTab,
                            onTabSelected = {tab->
                                currentTab = tab
                                when(tab){
                                    BottomTab.HOME -> navController.navigate(
                                        NavigationScreens.HomeScreen)
                                    BottomTab.SAVED-> navController.navigate(
                                        NavigationScreens.BookmarksScreen)
                                }
                            }
                            )
                    }
                    composable<NavigationScreens.DetailsScreen> {navBackStackEntry ->
                        val photo: NavigationScreens.DetailsScreen = navBackStackEntry.toRoute()
                        val certainPhoto = photos.find { it.title == photo.photoId }
                        DetailsScreen(
                            photo = certainPhoto,
                            onBack = {navController.popBackStack()},
                            onDownload = {},
                            onBookmark = {photo->
                                val updated = photo.copy(
                                    isSaved = !photo.isSaved
                                )
                                databaseViewModel.insertNewPhoto(updated)
                                networkViewModel.toggleBookmark(updated)

                            },
                            onExplore = {navController.navigate(
                                NavigationScreens.HomeScreen)})
                    }
                    composable<NavigationScreens.BookmarksScreen> {
                        Bookmarks(
                            photos = savedPhotos,
                            state = stateLoading,
                            onExplore = {navController.navigate(
                                NavigationScreens.HomeScreen)},
                            onClick = {photoId->
                                navController.navigate(NavigationScreens.DetailsScreen(photoId = photoId))},
                            currentTab = currentTab,
                            onTabSelected = {tab->
                                currentTab = tab
                                when(tab){
                                    BottomTab.HOME -> navController.navigate(
                                        NavigationScreens.HomeScreen)
                                    BottomTab.SAVED-> navController.navigate(
                                        NavigationScreens.BookmarksScreen)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
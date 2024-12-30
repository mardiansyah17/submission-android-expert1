package com.example.submissionbelajarcompose.presentation.navigation

sealed class NavigationGraph(val route: String) {
    data class DetailScreen(val id: String) : NavigationGraph("detail_screen/$id")
    data class EditScreen(val id: String) : NavigationGraph("edit_screen/$id")
}

sealed class TabNavigationGraph(val route: String) {
    data object HomeScreen : TabNavigationGraph("home_screen")
    data object CreateScreen : TabNavigationGraph("create_screen")
    data object FavoriteScreen : TabNavigationGraph("favorite_screen")
    data object AboutScreen : TabNavigationGraph("about_page")


}
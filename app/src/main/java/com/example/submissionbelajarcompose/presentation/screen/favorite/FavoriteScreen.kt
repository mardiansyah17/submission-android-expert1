package com.example.submissionbelajarcompose.presentation.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.submissionbelajarcompose.presentation.components.CardRecipe
import com.example.submissionbelajarcompose.presentation.components.EmptyLayout
import com.example.submissionbelajarcompose.presentation.components.PullToRefreshBox
import com.example.submissionbelajarcompose.presentation.navigation.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navHostController: NavHostController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {

    val listRecipe by viewModel.recipes.collectAsState()
    val state = rememberPullToRefreshState()
    val isRefreshing = remember { mutableStateOf(false) }
    val loading = viewModel.loading.collectAsState().value
    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing.value,
        onRefresh = {
            viewModel.getFavoriteRecipes()
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {


            if (loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                return@LazyColumn
            }
            item {
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .height(20.dp)
                )
            }
            if (listRecipe.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyLayout(
                            msg = "Tidak ada resep favorit"
                        )
                    }
                }
                return@LazyColumn
            }




            items(viewModel.recipes.value.size) { index ->
                val recipe = listRecipe[index]
                CardRecipe(
                    title = recipe.title,
                    description = recipe.description,
                    imageUrl = recipe.imageUrl,
                    onClick = {
                        navHostController.navigate(NavigationGraph.DetailScreen(recipe.id).route)
                    },
                    onEdit = {},
                    onDelete = {
                        viewModel.deleteRecipe(recipe.id, recipe.imageUrl)
                    }
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .height(20.dp)
                )
            }
        }

    }
}

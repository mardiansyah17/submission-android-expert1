package com.example.submissionbelajarcompose.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.presentation.components.AppButton
import com.example.submissionbelajarcompose.presentation.components.CardRecipe
import com.example.submissionbelajarcompose.presentation.components.EmptyLayout
import com.example.submissionbelajarcompose.presentation.components.InputTextField
import com.example.submissionbelajarcompose.presentation.components.PullToRefreshBox
import com.example.submissionbelajarcompose.presentation.navigation.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val stateRecipe = homeViewModel.recipes.collectAsState()
    val statePull = rememberPullToRefreshState()
    val isRefreshing = remember { mutableStateOf(false) }
    val loading = homeViewModel.loading.collectAsState().value


    when (val state = stateRecipe.value) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val listRecipe = state.data ?: emptyList()
            PullToRefreshBox(
                state = statePull,
                isRefreshing = isRefreshing.value,
                onRefresh = {
                    homeViewModel.getRecipes()
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(horizontal = 10.dp)

                ) {
                    item {
                        InputTextField(
                            text = homeViewModel.query.value,
                            maxLine = 1,
                            label = "Cari Resep",
                        ) {
                            homeViewModel.query.value = it
                            homeViewModel.searchRecipe(it)
                        }
                    }


                    // Empty State
                    if (listRecipe.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptyLayout(
                                    msg = "Hmm, masih kosong. Yuk, isi dengan resep lezat dan jadikan momen makan lebih bermakna!"
                                )
                            }
                        }
                        return@LazyColumn
                    }




                    items(listRecipe.size) { index ->
                        val recipe = listRecipe[index]
                        CardRecipe(
                            title = recipe.title,
                            description = recipe.description,
                            imageUrl = recipe.imageUrl,
                            onClick = {
                                navHostController.navigate(NavigationGraph.DetailScreen(recipe.id).route)
                            },
                            onEdit = {
                                navHostController.navigate(NavigationGraph.EditScreen(recipe.id).route)
                            },
                            onDelete = {
                                homeViewModel.deleteRecipe(recipe.id, recipe.imageUrl)
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

        is Resource.Error -> {
            Column {
                Text("ups, terjadi kesalahan silahkan coba lagi")
                AppButton(
                    "Coba Lagi",
                ) { }
            }
        }

    }


}

package com.example.submissionbelajarcompose.presentation.screen.detailRecipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.presentation.components.NotFoundLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailRecipeScreen(
    navHostController: NavHostController,
    viewModel: DetailRecipeViewModel = hiltViewModel(),
    id: String
) {

    val recipeState = viewModel.recipe.collectAsState()

    LaunchedEffect(id) {
        viewModel.getRecipe(id)

    }

    when (val state = recipeState.value) {

        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Error -> {
            NotFoundLayout()
        }

        is Resource.Success -> {
            val recipe = state.data!!
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navHostController.popBackStack()
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        title = {
                            Text("Detail Recipe")
                        },
                        actions = {
                            IconButton(
                                onClick = {

                                    viewModel.updateFavoriteRecipe(id, recipe.favorite == null)
                                }
                            ) {
                                if (recipe.favorite != null) {
                                    Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = "Back",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Icon(
                                        Icons.Default.FavoriteBorder,
                                        contentDescription = "Back",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    )
                }
            ) { padding ->

                Box(
                    modifier = Modifier.padding(padding)
                ) {


                    LazyColumn {

                        item {
                            Image(
                                painter = rememberAsyncImagePainter(recipe.imageUrl),
                                contentDescription = "Recipe Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        item {


                            Column(
                                Modifier.padding(10.dp)
                            ) {
                                Text(
                                    text = recipe.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Box(
                                    Modifier.defaultMinSize(minHeight = 100.dp)
                                ) {
                                    Text(
                                        recipe.description,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text("Bahan:", style = MaterialTheme.typography.titleMedium)
                            }
                        }

                        items(recipe.ingredients.size) {
                            val number = it + 1
                            Text(
                                "• ${recipe.ingredients[it]}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }


                    }
                }
            }
        }
    }


}


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showSystemUi = true)
//@Composable
//fun DetailRecipeScreenPreview() {
//    AppTheme {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//
//                            }
//                        ) {
//                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                        }
//                    },
//                    title = {
//                        Text("Detail Recipe")
//                    },
//                    actions = {
//                        IconButton(
//                            onClick = {}
//                        ) {
//                            Icon(Icons.Default.Favorite, contentDescription = "Favorite")
//                        }
//                    }
//                )
//            }
//        ) { padding ->
//
//            Box(
//                modifier = Modifier.padding(padding)
//            ) {
//
//
//                LazyColumn {
//
//                    item {
//                        Image(
//                            painter = painterResource(R.drawable.nasi_goreng),
//                            contentDescription = "Recipe Image",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(250.dp),
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                    item {
//
//
//                        Column(
//                            Modifier.padding(10.dp)
//                        ) {
//                            Text(
//                                text = "recipe.title",
//                                style = MaterialTheme.typography.titleMedium
//                            )
//                            Spacer(modifier = Modifier.height(10.dp))
//
//                            Box(
//                                Modifier.defaultMinSize(minHeight = 100.dp)
//                            ) {
//                                Text(
//                                    "recipe.description",
//                                    style = MaterialTheme.typography.bodyMedium
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(10.dp))
//                            Text("Bahan:", style = MaterialTheme.typography.titleMedium)
//                        }
//                    }
//
//                    items(4) {
//                        val number = it + 1
//                        Text(
//                            "• Bahan $number",
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(horizontal = 10.dp)
//                        )
//                        Spacer(modifier = Modifier.height(10.dp))
//                    }
//
//
//                }
//            }
//        }
//    }
//}
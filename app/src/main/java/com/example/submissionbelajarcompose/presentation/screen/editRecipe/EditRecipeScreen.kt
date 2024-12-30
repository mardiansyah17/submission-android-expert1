package com.example.submissionbelajarcompose.presentation.screen.editRecipe

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.submissionbelajarcompose.presentation.components.AppButton
import com.example.submissionbelajarcompose.presentation.components.DialogMessage
import com.example.submissionbelajarcompose.presentation.components.InputTextField
import com.example.submissionbelajarcompose.presentation.navigation.TabNavigationGraph

@Composable
fun EditRecipeScreen(
    navHostController: NavHostController,
    viewModel: EditRecipeViewModel = hiltViewModel(),
    id: String,
) {
    val uiInfo = viewModel.editRecipeUiInfo.collectAsStateWithLifecycle().value
    val context = LocalContext.current


    LaunchedEffect(id) {
        viewModel.getRecipe(id)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri = data?.data
            viewModel.onEvent(EditRecipeEvent.OnValueChange(uri.toString(), "imageUrl"))
        }
    }
    if (viewModel.successMsg.value.isNotEmpty()) {
        DialogMessage(

            message = viewModel.successMsg.value,
            confirmButton = {
                TextButton(onClick = {
                    viewModel.successMsg.value = ""
                    navHostController.navigate(TabNavigationGraph.HomeScreen.route) {
                        popUpTo(0)

                    }

                }) {
                    Text("Oke")
                }
            },
        )
    }

    if (viewModel.errorMsg.value.isNotEmpty()) {
        DialogMessage(
            message = viewModel.errorMsg.value,
            confirmButton = {
                TextButton(onClick = {
                    viewModel.errorMsg.value = ""
                }) {
                    Text("Oke")
                }
            }
        )
    }


    if (viewModel.loading.value) {
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    ConstraintLayout(
        Modifier.fillMaxSize()
    ) {
        val (content, button, image, editImageBtn) = createRefs()




        Box(
            Modifier
                .width(350.dp)
                .height(200.dp)
                .background(Color.Gray)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Image(
                painter = rememberAsyncImagePainter(uiInfo.imageUrl),
                contentDescription = "Image",
                Modifier
                    .fillMaxSize()
                    .height(200.dp),
                contentScale = ContentScale.Crop

            )

        }

        IconButton(
            onClick = {
                launcher.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            },
            modifier = Modifier
                .size(50.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(Color.Black.copy(alpha = 0.2f))
                .constrainAs(editImageBtn) {
                    top.linkTo(image.top, margin = 10.dp)
                    end.linkTo(image.end, margin = 10.dp)
                },

            ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Image")
        }

        LazyColumn(
            Modifier
                .padding(10.dp)
                .fillMaxSize()

                .constrainAs(content) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            item {
                Column {


                    InputTextField(
                        text = uiInfo.title,
                        onValueChange = {
                            viewModel.onEvent(EditRecipeEvent.OnValueChange(it, "title"))
                        },
                        label = "Nama Resep",
                        modifier = Modifier.padding(10.dp)
                    )


                    InputTextField(
                        text = uiInfo.description,
                        onValueChange = {
                            viewModel.onEvent(EditRecipeEvent.OnValueChange(it, "description"))
                        },
                        label = "Deskripsi",
                        maxLine = 2,

                        modifier = Modifier
                            .padding(10.dp)
                            .height(100.dp),
                    )

                    Text(
                        text = "Bahan - Bahan",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(10.dp)
                    )


                }
            }
            items(uiInfo.ingredients.size) {
                val number = it + 1
                InputTextField(
                    text = uiInfo.ingredients.getOrNull(it) ?: "",
                    onValueChange = { value ->
                        viewModel.onEvent(EditRecipeEvent.OnIngredientChange(value, it))
                    },
                    label = "Bahan $number",
                    modifier = Modifier.padding(10.dp)
                )
            }


        }
        AppButton("Simpan",
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {

            viewModel.onEvent(EditRecipeEvent.UpdateRecipe(id, context))

        }
    }
}


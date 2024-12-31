package com.example.submissionbelajarcompose.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun CardRecipe(
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    title: String,
    description: String,
    imageUrl: String,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var menuOffset by remember { mutableStateOf(Offset.Zero) }
    val density = LocalDensity.current

    Box(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                shape = MaterialTheme.shapes.medium
            )
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick()
                    },
                    onLongPress = { offset ->
                        menuOffset = offset // Ambil posisi klik
                        isMenuExpanded = true
                    }
                )
            }
    ) {
        Row {
            AsyncImage(
                model = imageUrl,
                
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .width(125.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,

                )
            Column {
                Text(
                    title,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.titleSmall,

                    )
                Text(
                    description,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            offset = with(density) { DpOffset(menuOffset.x.toDp(), menuOffset.y.toDp() - 100.dp) }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    isMenuExpanded = false
                    onEdit()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    isMenuExpanded = false
                    onDelete()
                }
            )
        }
    }
}


//@Composable
//@Preview(showSystemUi = true)
//fun CardRecipePreview() {
//    AppTheme {
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxSize()
//        ) {
//            items(4) {
//                CardRecipe(
//                    onClick = {},
//                    title = "Title",
//                    "Description",
//                    imageUrl = ""
//                )
//            }
//        }
//    }
//}
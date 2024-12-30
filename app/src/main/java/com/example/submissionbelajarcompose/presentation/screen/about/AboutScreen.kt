package com.example.submissionbelajarcompose.presentation.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.submissionbelajarcompose.ui.theme.AppTheme

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos-18899482832eed78787e8d7666ad89db20240910111221.png"),
            contentDescription = "Profile Image",
            modifier = Modifier.clip(shape = CircleShape)
        )

        Text("Muhammad Mardiansyah", style = MaterialTheme.typography.titleLarge)
        Text("mardiansyahm002@gmail.com", style = MaterialTheme.typography.titleSmall)
    }
}

@Preview(showSystemUi = true)
@Composable
fun AboutPreview() {
    AppTheme {
        AboutScreen()
    }
}
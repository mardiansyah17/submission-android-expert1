package com.example.submissionbelajarcompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.submissionbelajarcompose.R

@Composable
fun EmptyLayout(
    msg: String
) {
    val preloaderLottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.empty_animation)
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(

            composition = preloaderLottieComposition.value,
            progress = preloaderProgress,
            modifier = Modifier
                .height(400.dp)

        )

        Text(
            text = msg,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

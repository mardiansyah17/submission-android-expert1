package com.example.submissionbelajarcompose.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.submissionbelajarcompose.R
import com.example.submissionbelajarcompose.ui.theme.AppTheme

@Composable
fun NotFoundLayout() {
    val preloaderLottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.not_found_animation)
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )
    Box(Modifier.fillMaxSize()) {
        LottieAnimation(

            composition = preloaderLottieComposition.value,
            progress = preloaderProgress,
        )
    }
}

@Preview
@Composable
fun NotFoundLayoutPreview() {
    AppTheme {
        NotFoundLayout()
    }
}
package com.example.submissionbelajarcompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.submissionbelajarcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogMessage(
    dialogType: DialogType = DialogType.SUCCESS,
    message: String,
    dismissButton: @Composable (() -> Unit)? = null,
    confirmButton: @Composable (() -> Unit)? = null,
) {
    val preloaderLottieComposition = rememberLottieComposition(
        spec = when (dialogType) {
            DialogType.SUCCESS -> LottieCompositionSpec.RawRes(R.raw.success_animation)
            DialogType.ERROR -> LottieCompositionSpec.RawRes(R.raw.failed_animation)
        },
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,

        )

    BasicAlertDialog(

        onDismissRequest = {

        },
        properties = DialogProperties(

        ),
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LottieAnimation(
                    composition = preloaderLottieComposition.value,
                    progress = preloaderProgress,
                    modifier = Modifier
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text =
                    message,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    dismissButton?.let {
                        it()
                    }
                    confirmButton?.let {
                        it()
                    }
                }
            }
        }
    }


}

enum class DialogType {
    SUCCESS,
    ERROR,
}

@Preview(showBackground = true)
@Composable
fun DialogMessagePreview() {
    DialogMessage(message = "This is a message", dismissButton = {}, confirmButton = {})
}
package com.example.submissionbelajarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.submissionbelajarcompose.presentation.navigation.SetUpNavigationGraph
import com.example.submissionbelajarcompose.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                SetUpNavigationGraph()
            }
        }
    }
}
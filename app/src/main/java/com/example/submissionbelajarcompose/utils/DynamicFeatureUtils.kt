@file:Suppress("SpellCheckingInspection")

package com.example.submissionbelajarcompose.utils

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer

object DynamicFeatureUtils {

    private const val PACKAGE = "com.example."


    @Composable
    fun favoriteScreen(): Boolean {
        return loadDF(
            className = PACKAGE.plus("favorite.FavoriteScreen"),
            methodName = "FavoriteScreen"
        )
    }

    @Composable
    private fun loadDF(

        className: String,
        methodName: String,
        objectInstance: Any = Any()
    ): Boolean {
        val dfClass = loadClassByReflection(className)
        if (dfClass != null) {
            val composer = currentComposer
            val method = findMethodByReflection(
                dfClass,
                methodName
            )
            Log.i("MardiMaantap", "Method: $method")
            if (method != null) {
                val isMethodInvoked =
                    invokeMethod(method, objectInstance, composer, 0)
                Text("Method Invoked")
                //                    ShowDFNotFoundScreen(paddingValues = paddingValues)
                return isMethodInvoked
            } else {
                Text("Method Invoked")
//                ShowDFNotFoundScreen(paddingValues = paddingValues)
                return false
            }
        } else {
            Text("Method Invoked")
//            ShowDFNotFoundScreen(paddingValues = paddingValues)
            return false
        }
    }
}
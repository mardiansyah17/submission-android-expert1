package com.example.submissionbelajarcompose.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.domain.usecase.RecipeUseCase
import com.example.submissionbelajarcompose.favorite.presentation.favorite.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val recipeUseCase: RecipeUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(
                    useCase = recipeUseCase,
                ) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}
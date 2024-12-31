package com.example.submissionbelajarcompose.domain.usecase

import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.google.firebase.Timestamp
import io.reactivex.rxjava3.core.Flowable

interface RecipeUseCase {
    fun getRecipes(): Flowable<Resource<List<Recipe>>>
    suspend fun getRecipeById(id: String): Recipe
    suspend fun createRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: String)
    suspend fun getFavoriteRecipes(): List<Recipe>
    suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?)
    suspend fun searchRecipes(query: String): List<Recipe>

}
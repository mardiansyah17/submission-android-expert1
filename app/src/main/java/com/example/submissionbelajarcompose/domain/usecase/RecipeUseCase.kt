package com.example.submissionbelajarcompose.domain.usecase

import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.google.firebase.Timestamp
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface RecipeUseCase {
    fun getRecipes(): Flowable<Resource<List<Recipe>>>
    fun getRecipeById(id: String): Flowable<Resource<Recipe>>
    fun createRecipe(recipe: Recipe): Completable
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: String)
    fun getFavoriteRecipes(): Flowable<Resource<List<Recipe>>>
    suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?)
    suspend fun searchRecipes(query: String): List<Recipe>

}
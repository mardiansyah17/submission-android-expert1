package com.example.submissionbelajarcompose.data.source

import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.data.model.RecipeDto
import com.google.firebase.Timestamp
import io.reactivex.rxjava3.core.Flowable

interface IRecipeDataSource {
    fun getRecipesFromFirebase(): Flowable<Resource<List<RecipeDto>>>
    suspend fun getRecipeById(id: String): RecipeDto
    suspend fun createRecipe(recipe: RecipeDto)
    suspend fun updateRecipe(recipe: RecipeDto)
    suspend fun deleteRecipe(id: String)
    suspend fun getFavoriteRecipes(): List<RecipeDto>
    suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?)
    suspend fun searchRecipes(query: String): List<RecipeDto>

}
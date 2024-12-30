package com.example.submissionbelajarcompose.domain.repository

import com.example.submissionbelajarcompose.domain.model.Recipe
import com.google.firebase.Timestamp

interface IRecipeRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getRecipeById(id: String): Recipe
    suspend fun createRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: String)
    suspend fun getFavoriteRecipes(): List<Recipe>
    suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?)
    suspend fun searchRecipes(query: String): List<Recipe>

}
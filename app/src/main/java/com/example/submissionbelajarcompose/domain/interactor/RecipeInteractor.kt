package com.example.submissionbelajarcompose.domain.interactor

import android.util.Log
import com.example.submissionbelajarcompose.domain.usecase.RecipeUseCase
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.example.submissionbelajarcompose.domain.repository.IRecipeRepository
import com.google.firebase.Timestamp

class RecipeInteractor(
    private val repository: IRecipeRepository
) : RecipeUseCase {
    override suspend fun getRecipes(): List<Recipe> {
        return repository.getRecipes()
    }

    override suspend fun getRecipeById(id: String): Recipe {
        return repository.getRecipeById(id)
    }

    override suspend fun createRecipe(recipe: Recipe) {

        if (recipe.title.isEmpty()) {
            throw IllegalArgumentException("Judul tidak boleh kosong")
        }


        if (recipe.description.isEmpty()) {
            throw IllegalArgumentException("Deskripsi tidak boleh kosong")
        }

        if (recipe.ingredients.filter { it.isNotEmpty() }.size < 2) {
            throw IllegalArgumentException("Minimal 2 bahan")
        }

        repository.createRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        if (recipe.title.isEmpty()) {
            throw IllegalArgumentException("Judul tidak boleh kosong")
        }


        if (recipe.description.isEmpty()) {
            throw IllegalArgumentException("Deskripsi tidak boleh kosong")
        }

        if (recipe.ingredients.filter { it.isNotEmpty() }.size < 2) {
            throw IllegalArgumentException("Minimal 2 bahan")
        }
        repository.updateRecipe(recipe)
    }

    override suspend fun deleteRecipe(id: String) {
        repository.deleteRecipe(id)
    }

    override suspend fun getFavoriteRecipes(): List<Recipe> {
        return repository.getFavoriteRecipes()
    }

    override suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?) {
        repository.setFavoriteRecipe(id, favorite)
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return repository.searchRecipes(query)
    }

}
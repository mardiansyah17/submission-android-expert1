package com.example.submissionbelajarcompose.domain.interactor

import android.util.Log
import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.domain.usecase.RecipeUseCase
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.example.submissionbelajarcompose.domain.repository.IRecipeRepository
import com.google.firebase.Timestamp
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class RecipeInteractor(
    private val repository: IRecipeRepository
) : RecipeUseCase {
    override fun getRecipes(): Flowable<Resource<List<Recipe>>> {
        return repository.getRecipes()
    }

    override fun getRecipeById(id: String): Flowable<Resource<Recipe>> {
        return repository.getRecipeById(id)
    }

    override fun createRecipe(recipe: Recipe): Completable {

        if (recipe.title.isEmpty()) {
            throw IllegalArgumentException("Judul tidak boleh kosong")
        }


        if (recipe.description.isEmpty()) {
            throw IllegalArgumentException("Deskripsi tidak boleh kosong")
        }

        if (recipe.ingredients.filter { it.isNotEmpty() }.size < 2) {
            throw IllegalArgumentException("Minimal 2 bahan")
        }

        return repository.createRecipe(recipe)
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

    override fun getFavoriteRecipes(): Flowable<Resource<List<Recipe>>> {
        return repository.getFavoriteRecipes()
    }

    override suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?) {
        repository.setFavoriteRecipe(id, favorite)
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return repository.searchRecipes(query)
    }

}
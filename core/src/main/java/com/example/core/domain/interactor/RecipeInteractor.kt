package com.example.core.domain.interactor

import com.example.core.data.Resource
import com.example.core.domain.model.Recipe
import com.example.core.domain.repository.IRecipeRepository
import com.example.core.domain.usecase.RecipeUseCase
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

    override fun updateRecipe(recipe: Recipe): Completable {
        if (recipe.title.isEmpty()) {
            throw IllegalArgumentException("Judul tidak boleh kosong")
        }


        if (recipe.description.isEmpty()) {
            throw IllegalArgumentException("Deskripsi tidak boleh kosong")
        }

        if (recipe.ingredients.filter { it.isNotEmpty() }.size < 2) {
            throw IllegalArgumentException("Minimal 2 bahan")
        }
        return repository.updateRecipe(recipe)
    }

    override fun deleteRecipe(id: String): Completable {
        return repository.deleteRecipe(id)
    }

    override fun getFavoriteRecipes(): Flowable<Resource<List<Recipe>>> {
        return repository.getFavoriteRecipes()
    }

    override fun setFavoriteRecipe(id: String, favorite: Timestamp?): Completable {
        return repository.setFavoriteRecipe(id, favorite)
    }

    override fun searchRecipes(query: String): Flowable<Resource<List<Recipe>>> {
        return repository.searchRecipes(query)
    }

}
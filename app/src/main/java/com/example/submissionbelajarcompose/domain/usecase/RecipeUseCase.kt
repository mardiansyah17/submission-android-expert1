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
    fun updateRecipe(recipe: Recipe): Completable
    fun deleteRecipe(id: String): Completable
    fun getFavoriteRecipes(): Flowable<Resource<List<Recipe>>>
    fun setFavoriteRecipe(id: String, favorite: Timestamp?): Completable
    fun searchRecipes(query: String): Flowable<Resource<List<Recipe>>>

}
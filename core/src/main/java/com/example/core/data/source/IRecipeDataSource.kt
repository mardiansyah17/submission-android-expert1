package com.example.core.data.source

import com.example.core.data.Resource
import com.example.core.data.model.RecipeDto
import com.google.firebase.Timestamp
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface IRecipeDataSource {
    fun getRecipesFromFirebase(): Flowable<Resource<List<RecipeDto>>>
    fun getRecipeById(id: String): Flowable<Resource<RecipeDto>>
    fun createRecipe(recipe: RecipeDto): Completable
    fun updateRecipe(recipe: RecipeDto): Completable
    fun deleteRecipe(id: String): Completable
    fun getFavoriteRecipes(): Flowable<Resource<List<RecipeDto>>>
    fun setFavoriteRecipe(id: String, favorite: Timestamp?): Completable
    fun searchRecipes(query: String): Flowable<Resource<List<RecipeDto>>>

}
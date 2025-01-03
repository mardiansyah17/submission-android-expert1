package com.example.submissionbelajarcompose.data.repository

import android.util.Log
import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.data.model.toDomain
import com.example.submissionbelajarcompose.data.source.IRecipeDataSource
import com.example.submissionbelajarcompose.domain.repository.IRecipeRepository
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.example.submissionbelajarcompose.domain.model.toDto
import com.google.firebase.Timestamp
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class RecipeRepository(
    private val recipeDataSource: IRecipeDataSource
) : IRecipeRepository {
    override fun getRecipes(): Flowable<Resource<List<Recipe>>> {
        return recipeDataSource.getRecipesFromFirebase()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data?.map { it.toDomain() }
                        ?: emptyList())

                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(resource.message ?: "Error")
                }
            }
    }

    override fun getRecipeById(id: String): Flowable<Resource<Recipe>> {
        return recipeDataSource.getRecipeById(id)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data!!.toDomain())
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(resource.message ?: "Error")
                }
            }
    }

    override fun createRecipe(recipe: Recipe): Completable =
        recipeDataSource.createRecipe(recipe.toDto())


    override fun updateRecipe(recipe: Recipe): Completable {
        return recipeDataSource.updateRecipe(recipe.toDto())
    }

    override fun deleteRecipe(id: String): Completable {
        return recipeDataSource.deleteRecipe(id)
    }

    override fun getFavoriteRecipes(): Flowable<Resource<List<Recipe>>> {
        return recipeDataSource.getFavoriteRecipes()
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data?.map { it.toDomain() }
                        ?: emptyList())

                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(resource.message ?: "Error")
                }
            }
    }

    override fun setFavoriteRecipe(id: String, favorite: Timestamp?): Completable {
        return recipeDataSource.setFavoriteRecipe(id, favorite)
    }

    override fun searchRecipes(query: String): Flowable<Resource<List<Recipe>>> {
        return recipeDataSource.searchRecipes(query)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data?.map { it.toDomain() }
                        ?: emptyList())

                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(resource.message ?: "Error")
                }
            }
    }
}
package com.example.submissionbelajarcompose.data.source

import android.util.Log
import com.example.submissionbelajarcompose.data.model.RecipeDto
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : IRecipeDataSource {
    override suspend fun getRecipesFromFirebase(): List<RecipeDto> {
        val querySnapshot = firestore.collection("recipes")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
        val mapData = querySnapshot.documents.map { document ->
            document.toObject(RecipeDto::class.java)!!
                .copy(id = document.id)

        }
        return mapData
    }

    override suspend fun getRecipeById(id: String): RecipeDto {
        try {
            val documentSnapshot = firestore.collection("recipes")
                .document(id)
                .get()
                .await()
            return documentSnapshot.toObject(RecipeDto::class.java)!!
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error get recipe by id: ${e.message}")
            throw Exception("Error get recipe by id")
        }
    }

    override suspend fun createRecipe(recipe: RecipeDto) {
        try {
            firestore.collection("recipes")
                .add(recipe)
                .await()
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error create recipe: ${e.message}")
            throw Exception("Error create recipe")
        }
    }

    override suspend fun updateRecipe(recipe: RecipeDto) {
        try {

            firestore.collection("recipes")
                .document(recipe.id)
                .set(recipe)
                .await()
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error update recipe: ${e.message}")
            throw Exception("Error update recipe")
        }
    }

    override suspend fun deleteRecipe(id: String) {
        try {
            firestore.collection("recipes")
                .document(id)
                .delete()
                .await()
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error delete recipe: ${e.message}")
            throw Exception("Error delete recipe")
        }
    }

    override suspend fun getFavoriteRecipes(): List<RecipeDto> {
        try {
            val querySnapshot = firestore.collection("recipes")
                .whereNotEqualTo("favorite", null)
                .orderBy("favorite", Query.Direction.DESCENDING)
                .get()
                .await()
            val mapData = querySnapshot.documents.map { document ->
                document.toObject(RecipeDto::class.java)!!
                    .copy(id = document.id)

            }
            return mapData
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error getting favorite recipes: ${e.message}")
            throw Exception("Error getting favorite recipes")
        }
    }

    override suspend fun setFavoriteRecipe(id: String, favorite: Timestamp?) {
        try {
            firestore.collection("recipes")
                .document(id)
                .update("favorite", favorite)
                .await()
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error set favorite recipe: ${e.message}")
            throw Exception("Error set favorite recipe")
        }
    }

    override suspend fun searchRecipes(query: String): List<RecipeDto> {
        try {
            val querySnapshot = firestore.collection("recipes")
                .orderBy("titleLower")
                .startAt(query)
                .endAt(query + "\uf8ff")

                .get()
                .await()
            val mapData = querySnapshot.documents.map { document ->
                document.toObject(RecipeDto::class.java)!!
                    .copy(id = document.id)

            }
            return mapData.sortedByDescending { it.createdAt }
        } catch (e: Exception) {
            Log.e("RecipeDataSource", "Error search recipe: ${e.message}")
            throw Exception("Error search recipe")
        }
    }
}
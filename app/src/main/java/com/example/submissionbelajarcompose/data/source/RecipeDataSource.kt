package com.example.submissionbelajarcompose.data.source

import android.util.Log
import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.data.model.RecipeDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : IRecipeDataSource {
    override fun getRecipesFromFirebase(): Flowable<Resource<List<RecipeDto>>> {
        return Flowable.create<Resource<List<RecipeDto>>>({ emitter ->
            firestore.collection("recipes")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val mapData = querySnapshot.documents.map { document ->
                        document.toObject(RecipeDto::class.java)!!
                            .copy(id = document.id)
                    }
                    emitter.onNext(Resource.Success(mapData.map { it }))
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onNext(Resource.Error(exception.message ?: "Ada kesalahan", null))
                    emitter.onComplete()
                }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
    }

    override fun getRecipeById(id: String): Flowable<Resource<RecipeDto>> {
        return Flowable.create<Resource<RecipeDto>>({ emitter ->
            firestore.collection("recipes")
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val recipe = documentSnapshot.toObject(RecipeDto::class.java)

                    if (recipe != null) {
                        emitter.onNext(Resource.Success(recipe.copy(id = documentSnapshot.id)))
                        emitter.onComplete()
                    } else {
                        emitter.onNext(Resource.Error("Data tidak ditemukan", null))
                        emitter.onComplete()
                    }

                }
                .addOnFailureListener { exception ->
                    emitter.onNext(Resource.Error(exception.message ?: "Ada kesalahan", null))
                    emitter.onComplete()
                }

        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
    }

    override fun createRecipe(recipe: RecipeDto): Completable = Completable.create { emitter ->
        firestore.collection("recipes")
            .add(recipe)
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener {
                emitter.onError(it)
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

    override fun getFavoriteRecipes(): Flowable<Resource<List<RecipeDto>>> {
        return Flowable.create<Resource<List<RecipeDto>>>({ emitter ->
            firestore.collection("recipes")
                .whereEqualTo("favorite", null)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val mapData = querySnapshot.documents.map { document ->
                        document.toObject(RecipeDto::class.java)!!
                            .copy(id = document.id)
                    }
                    emitter.onNext(Resource.Success(mapData.map { it }))
                    emitter.onComplete()
                }
                .addOnFailureListener { exception ->
                    emitter.onNext(Resource.Error(exception.message ?: "Ada kesalahan", null))
                    emitter.onComplete()
                }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
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
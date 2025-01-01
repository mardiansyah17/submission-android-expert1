package com.example.core.data.source

import com.example.core.data.Resource
import com.example.core.data.model.RecipeDto
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
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
            emitter.onNext(Resource.Loading())
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

    override fun updateRecipe(recipe: RecipeDto): Completable = Completable.create { emitter ->
        firestore.collection("recipes")
            .document(recipe.id)
            .set(recipe)
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun deleteRecipe(id: String): Completable = Completable.create { emitter ->

        firestore.collection("recipes")
            .document(id)
            .delete()
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun getFavoriteRecipes(): Flowable<Resource<List<RecipeDto>>> {
        return Flowable.create<Resource<List<RecipeDto>>>({ emitter ->
            firestore.collection("recipes")
                .whereNotEqualTo("favorite", null)
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

    override fun setFavoriteRecipe(id: String, favorite: Timestamp?): Completable =
        Completable.create { emitter ->
            firestore.collection("recipes")
                .document(id)
                .update("favorite", favorite)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }


    override fun searchRecipes(query: String): Flowable<Resource<List<RecipeDto>>> {
        return Flowable.create<Resource<List<RecipeDto>>>({ emitter ->
            firestore.collection("recipes")
                .orderBy("titleLower")
                .startAt(query)
                .endAt(query + "\uf8ff")
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
}
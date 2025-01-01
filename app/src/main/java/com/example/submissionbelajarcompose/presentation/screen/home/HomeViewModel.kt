package com.example.submissionbelajarcompose.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Recipe
import com.example.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val recipeUseCase: RecipeUseCase

) : ViewModel() {

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>>(Resource.Loading())
    val recipes: StateFlow<Resource<List<Recipe>>> = _recipes


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val query = mutableStateOf("")

    private val compositeDisposable = CompositeDisposable()

    init {
        getRecipes()
    }

    fun getRecipes() {
        _loading.value = true
        val disposable = recipeUseCase.getRecipes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _recipes.value = it
                _loading.value = false
            }, {
                _recipes.value = Resource.Error(it.localizedMessage ?: "Unknown error")
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun deleteRecipe(id: String, urlImage: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val bucket = supabaseClient.storage.from("recipe")
                bucket.delete(listOf(urlImage.substringAfter("recipe/")))
                recipeUseCase.deleteRecipe(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getRecipes()
                        _loading.value = false
                    }, {
                        Log.e(TAG, "deleteRecipe: ", it)
                    })
            } catch (e: Exception) {
                Log.e(TAG, "deleteRecipe: ", e)
            }
        }
    }

    fun searchRecipe(text: String) {
        val disposable = recipeUseCase.searchRecipes(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _recipes.value = it
            }, {
                _recipes.value = Resource.Error(it.localizedMessage ?: "Unknown error")
            })

        compositeDisposable.add(disposable)
    }


    companion object {
        private const val TAG = "HomeViewModel"
    }
}
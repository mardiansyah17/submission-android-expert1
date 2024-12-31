package com.example.submissionbelajarcompose.presentation.screen.favorite

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionbelajarcompose.data.Resource
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.example.submissionbelajarcompose.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val useCase: RecipeUseCase

) : ViewModel() {

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>>(Resource.Loading())
    val recipes: StateFlow<Resource<List<Recipe>>> = _recipes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val compositeDisposable = CompositeDisposable()

    init {
        getFavoriteRecipes()
    }

    fun getFavoriteRecipes() {
        val disposable = useCase.getFavoriteRecipes()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _recipes.value = it
            }, {
                _recipes.value = Resource.Error(it.localizedMessage ?: "Unknown error")
            })

        compositeDisposable.add(disposable)
    }

    fun deleteRecipe(id: String, urlImage: String) {
        viewModelScope.launch {
            try {
                val bucket = supabaseClient.storage.from("recipe")
                bucket.delete(listOf(urlImage.substringAfter("recipe/")))
                useCase.deleteRecipe(id)
                getFavoriteRecipes()
            } catch (e: Exception) {
                Log.e(TAG, "deleteRecipe: ", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
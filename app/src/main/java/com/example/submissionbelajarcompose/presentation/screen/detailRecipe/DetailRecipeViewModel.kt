package com.example.submissionbelajarcompose.presentation.screen.detailRecipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Recipe
import com.example.core.domain.usecase.RecipeUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {
    private val _recipe = MutableStateFlow<Resource<Recipe>>(Resource.Loading())
    val recipe: StateFlow<Resource<Recipe>> = _recipe


    private val compositeDisposable = CompositeDisposable()


    fun getRecipe(id: String) {
        val disposable = recipeUseCase.getRecipeById(id)
            .subscribe({
                _recipe.value = it
            }, {
                _recipe.value = Resource.Error(it.localizedMessage ?: "Unknown error")
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun updateFavoriteRecipe(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            val favorit = if (isFavorite) {
                Timestamp.now()
            } else {
                null
            }
            recipeUseCase.setFavoriteRecipe(id = id, favorite = favorit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _recipe.value = _recipe.value.data!!.copy(favorite = favorit).let {
                        Resource.Success(it)
                    }
                }, {
                    Log.e(TAG, "updateFavoriteRecipe: Error", it)
                })
        }
    }

    companion object {
        private const val TAG = "DetailRecipeViewModel"
    }

}
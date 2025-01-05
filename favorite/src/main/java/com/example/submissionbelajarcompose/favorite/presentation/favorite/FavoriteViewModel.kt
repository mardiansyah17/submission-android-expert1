package com.example.submissionbelajarcompose.favorite.presentation.favorite

import androidx.lifecycle.ViewModel
import com.example.core.data.Resource
import com.example.core.domain.model.Recipe
import com.example.core.domain.usecase.RecipeUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FavoriteViewModel(
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

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
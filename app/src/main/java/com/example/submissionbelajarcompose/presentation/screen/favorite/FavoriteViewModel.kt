package com.example.submissionbelajarcompose.presentation.screen.favorite

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionbelajarcompose.domain.model.Recipe
import com.example.submissionbelajarcompose.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val useCase: RecipeUseCase

) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val query = mutableStateOf("")

    init {
        getFavoriteRecipes()
    }

    fun getFavoriteRecipes() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val recipesResult = useCase.getFavoriteRecipes()
                _recipes.value = recipesResult
            } catch (e: Exception) {
                Log.e(TAG, "getRecipes: ", e)
            } finally {
                _loading.value = false
            }
        }
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


    companion object {
        private const val TAG = "HomeViewModel"
    }
}
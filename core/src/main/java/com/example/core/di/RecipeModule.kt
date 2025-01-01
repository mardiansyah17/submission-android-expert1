package com.example.core.di

import com.example.core.data.repository.RecipeRepository
import com.example.core.data.source.IRecipeDataSource
import com.example.core.data.source.RecipeDataSource
import com.example.core.domain.repository.IRecipeRepository
import com.example.core.domain.interactor.RecipeInteractor
import com.example.core.domain.usecase.RecipeUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RecipeModule {

    @Provides
    @Singleton
    fun provideRecipeUseCase(recipeRepository: IRecipeRepository): RecipeUseCase {
        return RecipeInteractor(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDataSource: IRecipeDataSource): IRecipeRepository {
        return RecipeRepository(recipeDataSource)
    }

    @Provides
    @Singleton
    fun provideRecipeDataSource(): IRecipeDataSource {
        return RecipeDataSource(
            firestore = FirebaseFirestore.getInstance()
        )
    }
}
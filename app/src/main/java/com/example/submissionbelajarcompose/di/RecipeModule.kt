package com.example.submissionbelajarcompose.di

import com.example.submissionbelajarcompose.data.source.IRecipeDataSource
import com.example.submissionbelajarcompose.data.source.RecipeDataSource
import com.example.submissionbelajarcompose.data.repository.RecipeRepository
import com.example.submissionbelajarcompose.domain.repository.IRecipeRepository
import com.example.submissionbelajarcompose.domain.interactor.RecipeInteractor
import com.example.submissionbelajarcompose.domain.usecase.RecipeUseCase
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
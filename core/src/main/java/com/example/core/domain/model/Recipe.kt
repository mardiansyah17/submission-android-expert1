package com.example.core.domain.model

import com.example.core.data.model.RecipeDto
import com.google.firebase.Timestamp

data class Recipe(
    val id: String,
    val createdAt: Timestamp,
    val title: String,
    val description: String,
    val imageUrl: String,
    val ingredients: List<String>,
    var titleLower: String = "",
    val favorite: Timestamp? = null,
)

fun Recipe.toDto(): RecipeDto {
    return RecipeDto(
        id = id,
        createdAt = createdAt,
        title = title,
        description = description,
        imageUrl = imageUrl,
        ingredients = ingredients,
        titleLower = titleLower,
        favorite = favorite

    )
}
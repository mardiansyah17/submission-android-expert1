package com.example.submissionbelajarcompose.data.model

import com.example.submissionbelajarcompose.domain.model.Recipe
import com.google.firebase.Timestamp
import java.util.Locale

data class RecipeDto(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val ingredients: List<String> = emptyList(),
    val titleLower: String = title.lowercase(Locale.ROOT),
    val createdAt: Timestamp = Timestamp.now(),
    val favorite: Timestamp? = null
)

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        ingredients = ingredients,
        titleLower = titleLower,
        createdAt = createdAt,
        favorite = favorite
    )
}

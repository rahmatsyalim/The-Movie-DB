package com.syalim.themoviedb.data.remote.dto.movie_review


import com.google.gson.annotations.SerializedName

data class ReviewItemDto(
    @SerializedName("author")
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetailsDto?,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String?
)
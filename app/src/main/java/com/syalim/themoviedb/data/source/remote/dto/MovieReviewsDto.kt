package com.syalim.themoviedb.data.source.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieReviewsDto(
   @SerializedName("id")
   val id: Int,
   @SerializedName("page")
   val page: Int,
   @SerializedName("results")
   val results: List<MovieReviewDto>,
   @SerializedName("total_pages")
   val totalPages: Int,
   @SerializedName("total_results")
   val totalResults: Int
)
data class MovieReviewDto(
   @SerializedName("author")
   val author: String,
   @SerializedName("author_details")
   val authorDetails: MovieReviewAuthorDetailsDto,
   @SerializedName("content")
   val content: String,
   @SerializedName("created_at")
   val createdAt: String,
   @SerializedName("id")
   val id: String,
   @SerializedName("updated_at")
   val updatedAt: String,
   @SerializedName("url")
   val url: String
)

data class MovieReviewAuthorDetailsDto(
   @SerializedName("avatar_path")
   val avatarPath: String?,
   @SerializedName("name")
   val name: String,
   @SerializedName("rating")
   val rating: Double?,
   @SerializedName("username")
   val username: String
)
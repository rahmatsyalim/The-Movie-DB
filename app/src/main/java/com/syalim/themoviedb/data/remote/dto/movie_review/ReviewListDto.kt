package com.syalim.themoviedb.data.remote.dto.movie_review


import com.google.gson.annotations.SerializedName

data class ReviewListDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ReviewItemDto>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)
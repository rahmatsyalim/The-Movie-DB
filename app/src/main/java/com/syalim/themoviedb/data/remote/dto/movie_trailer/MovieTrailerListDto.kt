package com.syalim.themoviedb.data.remote.dto.movie_trailer


import com.google.gson.annotations.SerializedName

data class MovieTrailerListDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<MovieTrailerItemDto>?
)
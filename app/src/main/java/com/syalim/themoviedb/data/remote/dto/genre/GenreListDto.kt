package com.syalim.themoviedb.data.remote.dto.genre


import com.google.gson.annotations.SerializedName

data class GenreListDto(
    @SerializedName("genres")
    val genres: List<GenreDto>? = emptyList()
)
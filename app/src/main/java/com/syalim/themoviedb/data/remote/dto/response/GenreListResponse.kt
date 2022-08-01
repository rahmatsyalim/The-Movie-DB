package com.syalim.themoviedb.data.remote.dto.response


import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres")
    val genres: List<GenreItemResponse>?
)

data class GenreItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
package com.syalim.themoviedb.data.remote.dto.movie_review


import com.google.gson.annotations.SerializedName

data class AuthorDetailsDto(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("username")
    val username: String?
)
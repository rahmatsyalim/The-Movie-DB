package com.syalim.themoviedb.data.source.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieGenresDto(
   @SerializedName("genres")
   val genres: List<MovieGenreDto>
)

data class MovieGenreDto(
   @SerializedName("id")
   val id: Int,
   @SerializedName("name")
   val name: String
)
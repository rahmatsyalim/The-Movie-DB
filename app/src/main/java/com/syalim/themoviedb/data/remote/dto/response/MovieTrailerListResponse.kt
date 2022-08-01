package com.syalim.themoviedb.data.remote.dto.response


import com.google.gson.annotations.SerializedName

data class MovieTrailerListResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<MovieTrailerItemResponse?>?
)

data class MovieTrailerItemResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("iso_3166_1")
    val iso31661: String? = null,
    @SerializedName("iso_639_1")
    val iso6391: String? = null,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("official")
    val official: Boolean? = null,
    @SerializedName("published_at")
    val publishedAt: String? = null,
    @SerializedName("site")
    val site: String? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("type")
    val type: String? = null
)
package com.syalim.themoviedb.data.remote.dto.movie_detail


import com.google.gson.annotations.SerializedName

data class ProductionCompanyDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)
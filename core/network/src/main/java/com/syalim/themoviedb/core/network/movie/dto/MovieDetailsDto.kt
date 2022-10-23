package com.syalim.themoviedb.core.network.movie.dto


import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
   @SerializedName("adult")
   val adult: Boolean,
   @SerializedName("backdrop_path")
   val backdropPath: String?,
   @SerializedName("belongs_to_collection")
   val belongsToCollection: Any?,
   @SerializedName("budget")
   val budget: Long,
   @SerializedName("genres")
   val genres: List<MovieDetailGenreDto>,
   @SerializedName("homepage")
   val homepage: String?,
   @SerializedName("id")
   val id: Int,
   @SerializedName("imdb_id")
   val imdbId: String?,
   @SerializedName("original_language")
   val originalLanguage: String,
   @SerializedName("original_title")
   val originalTitle: String,
   @SerializedName("overview")
   val overview: String?,
   @SerializedName("popularity")
   val popularity: Double,
   @SerializedName("poster_path")
   val posterPath: String?,
   @SerializedName("production_companies")
   val productionCompanies: List<MovieDetailProductionCompanyDto>,
   @SerializedName("production_countries")
   val productionCountries: List<MovieDetailProductionCountryDto>,
   @SerializedName("release_date")
   val releaseDate: String,
   @SerializedName("revenue")
   val revenue: Long,
   @SerializedName("runtime")
   val runtime: Int?,
   @SerializedName("spoken_languages")
   val spokenLanguages: List<MovieDetailSpokenLanguageDto>,
   @SerializedName("status")
   val status: String,
   @SerializedName("tagline")
   val tagline: String?,
   @SerializedName("title")
   val title: String,
   @SerializedName("video")
   val video: Boolean,
   @SerializedName("vote_average")
   val voteAverage: Double,
   @SerializedName("vote_count")
   val voteCount: Int,
   @SerializedName("videos")
   val videos: MovieTrailersDto,
   @SerializedName("recommendations")
   val recommendations: MoviesDto,
   @SerializedName("reviews")
   val reviews: MovieReviewsDto
)

data class MovieDetailGenreDto(
   @SerializedName("id")
   val id: Int,
   @SerializedName("name")
   val name: String
)

data class MovieDetailProductionCompanyDto(
   @SerializedName("id")
   val id: Int,
   @SerializedName("logo_path")
   val logoPath: String?,
   @SerializedName("name")
   val name: String,
   @SerializedName("origin_country")
   val originCountry: String
)

data class MovieDetailProductionCountryDto(
   @SerializedName("iso_3166_1")
   val iso31661: String,
   @SerializedName("name")
   val name: String
)

data class MovieDetailSpokenLanguageDto(
   @SerializedName("iso_639_1")
   val iso6391: String,
   @SerializedName("name")
   val name: String
)

data class MovieTrailersDto(
   @SerializedName("id")
   val id: Int,
   @SerializedName("results")
   val results: List<MovieTrailerDto>
)

data class MovieTrailerDto(
   @SerializedName("id")
   val id: String,
   @SerializedName("iso_3166_1")
   val iso31661: String,
   @SerializedName("iso_639_1")
   val iso6391: String,
   @SerializedName("key")
   val key: String,
   @SerializedName("name")
   val name: String,
   @SerializedName("official")
   val official: Boolean,
   @SerializedName("published_at")
   val publishedAt: String,
   @SerializedName("site")
   val site: String,
   @SerializedName("size")
   val size: Int,
   @SerializedName("type")
   val type: String
)


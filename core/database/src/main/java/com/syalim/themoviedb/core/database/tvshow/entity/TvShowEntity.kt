package com.syalim.themoviedb.core.database.tvshow.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@Entity(tableName = "tv_shows", primaryKeys = ["id", "category"])
data class TvShowEntity(
   val id: Int,
   val name: String,
   @ColumnInfo(name = "poster_path") val posterPath: String?,
   @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
   @ColumnInfo(name = "first_air_date") val firstAirDate: String?,
   @ColumnInfo(name = "vote_average") val voteAverage: Double,
   @ColumnInfo(name = "vote_count") val voteCount: Int,
   val category: String,
   @ColumnInfo(name = "updated_at") val updatedAt: Date = Date()
)

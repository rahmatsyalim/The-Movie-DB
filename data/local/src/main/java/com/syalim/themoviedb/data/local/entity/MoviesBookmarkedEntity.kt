package com.syalim.themoviedb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Entity(tableName = "movies_bookmarked")
data class MoviesBookmarkedEntity(
   @PrimaryKey val id: Int,
   val title: String,
   @ColumnInfo(name = "poster_path") val posterPath: String?,
   @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
   @ColumnInfo(name = "release_date") val releaseDate: String?,
   @ColumnInfo(name = "vote_average") val voteAverage: Double,
   @ColumnInfo(name = "vote_count") val voteCount: Int,
   @ColumnInfo(name = "updated_at") val updatedAt: Date = Date()
)
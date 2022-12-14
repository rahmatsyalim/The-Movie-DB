package com.syalim.themoviedb.core.database.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Entity(tableName = "movies_genres")
data class MovieGenresEntity(
   @PrimaryKey val id: Int,
   val name: String
)

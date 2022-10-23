package com.syalim.themoviedb.core.database.tvshow.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syalim.themoviedb.core.database.tvshow.entity.TvShowEntity


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@Dao
interface TvShowsDao {
   @Query("SELECT * FROM tv_shows WHERE category =:category")
   suspend fun getTvShows(category: String): List<TvShowEntity>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertTvShows(data: List<TvShowEntity>)

   @Query("DELETE FROM tv_shows WHERE category=:category")
   suspend fun deleteTvShows(category: String)
}
package com.syalim.themoviedb.data.repository

import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.domain.repository.MovieRepository
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
class MovieRepositoryImpl @Inject constructor(
   private val movieApi: MovieApi
) : MovieRepository {
}
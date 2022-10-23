package com.syalim.themoviedb.feature.movies.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.syalim.themoviedb.feature.movies.domain.model.Movie


/**
 * Created by Rahmat Syalim on 2022/09/07
 * rahmatsyalim@gmail.com
 */

object MovieDataComparator : DiffUtil.ItemCallback<Movie>() {
   override fun areItemsTheSame(
      oldItem: Movie,
      newItem: Movie
   ): Boolean {
      return oldItem.id == newItem.id
   }

   override fun areContentsTheSame(
      oldItem: Movie,
      newItem: Movie
   ): Boolean {
      return oldItem == newItem
   }
}
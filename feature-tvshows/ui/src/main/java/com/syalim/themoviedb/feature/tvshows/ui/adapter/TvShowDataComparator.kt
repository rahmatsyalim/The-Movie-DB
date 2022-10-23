package com.syalim.themoviedb.feature.tvshows.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow


/**
 * Created by Rahmat Syalim on 2022/09/07
 * rahmatsyalim@gmail.com
 */

object TvShowDataComparator : DiffUtil.ItemCallback<TvShow>() {
   override fun areItemsTheSame(
      oldItem: TvShow,
      newItem: TvShow
   ): Boolean {
      return oldItem.id == newItem.id
   }

   override fun areContentsTheSame(
      oldItem: TvShow,
      newItem: TvShow
   ): Boolean {
      return oldItem == newItem
   }
}
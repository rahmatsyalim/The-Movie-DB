package com.syalim.themoviedb.presentation.main.home

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.core.common.Constants.MOVIES_IN_THEATER
import com.syalim.themoviedb.databinding.ItemMovieCarouselBinding
import com.syalim.themoviedb.databinding.ItemMovieListBinding
import com.syalim.themoviedb.presentation.main.home.HomeAdapter.ViewType.CAROUSEL
import com.syalim.themoviedb.presentation.main.home.HomeAdapter.ViewType.MOVIE
import com.syalim.themoviedb.presentation.utils.BaseNestedAdapter
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */

class HomeAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : BaseNestedAdapter<com.syalim.themoviedb.domain.movie.model.Movies, HomeAdapterViewHolder>(DataComparator) {

   var onItemNavigation: ((Int, Bundle) -> Unit)? = null
   var onViewMoreClicked: ((Int, Bundle) -> Unit)? = null
   var carouselPosition: (ViewPager2.() -> Unit)? = null
   var coroutineScope: CoroutineScope? = null

   private enum class ViewType {
      CAROUSEL,
      MOVIE,
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
      return when (viewType) {
         CAROUSEL.ordinal -> HomeAdapterViewHolder.CarouselViewHolder(
            parent.viewBinding(ItemMovieCarouselBinding::inflate),
            coilImageLoader,
            onItemNavigation,
            onViewMoreClicked,
            carouselPosition,
            coroutineScope
         )
         MOVIE.ordinal -> HomeAdapterViewHolder.MoviesViewHolder(
            parent.viewBinding(ItemMovieListBinding::inflate),
            coilImageLoader,
            onItemNavigation
         )
         else -> throw ClassNotFoundException()
      }
   }

   override fun getItemViewType(position: Int): Int {
      return if (getItem(position).category == MOVIES_IN_THEATER) CAROUSEL.ordinal else MOVIE.ordinal
   }

   override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
      when (getItemViewType(position)) {
         CAROUSEL.ordinal -> (holder as HomeAdapterViewHolder.CarouselViewHolder).onBind(getItem(position))
         MOVIE.ordinal -> (holder as HomeAdapterViewHolder.MoviesViewHolder).onBind(getItem(position))
         else -> throw ClassNotFoundException()
      }
      super.onBindViewHolder(holder, position)
   }

   private object DataComparator : DiffUtil.ItemCallback<com.syalim.themoviedb.domain.movie.model.Movies>() {
      override fun areItemsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.Movies,
         newItem: com.syalim.themoviedb.domain.movie.model.Movies
      ): Boolean {
         return oldItem.category == newItem.category && oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.Movies,
         newItem: com.syalim.themoviedb.domain.movie.model.Movies
      ): Boolean {
         return oldItem == newItem
      }
   }
}
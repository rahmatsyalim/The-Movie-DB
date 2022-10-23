package com.syalim.themoviedb.feature.movies.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.ui.BaseNestedAdapter
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowCarouselListBinding
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowListBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.domain.model.MoviesCategorized
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */

class MoviesAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : BaseNestedAdapter<MoviesCategorized, MoviesAdapterViewHolder>(DataComparator) {

   var setupCarousel: (ViewPager2.() -> Unit)? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapterViewHolder {
      return when (viewType) {
         MovieCategory.IN_THEATER.ordinal -> MoviesAdapterViewHolder.MoviesCarouselViewHolder(
            parent.viewBinding(ItemMovieTvshowCarouselListBinding::inflate),
            customImageLoader,
            setupCarousel
         )
         else -> MoviesAdapterViewHolder.MoviesViewHolder(
            parent.viewBinding(ItemMovieTvshowListBinding::inflate),
            customImageLoader
         )
      }
   }

   override fun getItemViewType(position: Int): Int {
      return getItem(position).category.ordinal
   }

   override fun onBindViewHolder(holder: MoviesAdapterViewHolder, position: Int) {
      when (getItemViewType(position)) {
         MovieCategory.IN_THEATER.ordinal ->
            (holder as MoviesAdapterViewHolder.MoviesCarouselViewHolder).onBind(getItem(position))
         else -> (holder as MoviesAdapterViewHolder.MoviesViewHolder).onBind(getItem(position))
      }
      super.onBindViewHolder(holder, position)
   }

   private object DataComparator : DiffUtil.ItemCallback<MoviesCategorized>() {
      override fun areItemsTheSame(
         oldItem: MoviesCategorized,
         newItem: MoviesCategorized
      ): Boolean {
         return oldItem.category.ordinal == newItem.category.ordinal
      }

      override fun areContentsTheSame(
         oldItem: MoviesCategorized,
         newItem: MoviesCategorized
      ): Boolean {
         return oldItem == newItem
      }
   }
}
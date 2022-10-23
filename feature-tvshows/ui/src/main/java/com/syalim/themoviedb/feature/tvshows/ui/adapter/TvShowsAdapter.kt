package com.syalim.themoviedb.feature.tvshows.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.core.ui.BaseNestedAdapter
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowCarouselListBinding
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowListBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */

class TvShowsAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : BaseNestedAdapter<TvShowsCategorized, TvShowsAdapterViewHolder>(DataComparator) {

   var setupCarousel: (ViewPager2.() -> Unit)? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsAdapterViewHolder {
      return when (viewType) {
         TvShowCategory.ON_THE_AIR.ordinal -> TvShowsAdapterViewHolder.TvShowCarouselViewHolder(
            parent.viewBinding(ItemMovieTvshowCarouselListBinding::inflate),
            customImageLoader,
            setupCarousel
         )
         else -> TvShowsAdapterViewHolder.TvShowViewHolder(
            parent.viewBinding(ItemMovieTvshowListBinding::inflate),
            customImageLoader
         )
      }
   }

   override fun getItemViewType(position: Int): Int {
      return getItem(position).category.ordinal
   }

   override fun onBindViewHolder(holder: TvShowsAdapterViewHolder, position: Int) {
      when (getItemViewType(position)) {
         TvShowCategory.ON_THE_AIR.ordinal -> (holder as TvShowsAdapterViewHolder.TvShowCarouselViewHolder)
            .onBind(
               getItem(position)
            )
         else -> (holder as TvShowsAdapterViewHolder.TvShowViewHolder).onBind(getItem(position))
      }
      super.onBindViewHolder(holder, position)
   }

   private object DataComparator : DiffUtil.ItemCallback<TvShowsCategorized>() {
      override fun areItemsTheSame(
         oldItem: TvShowsCategorized,
         newItem: TvShowsCategorized
      ): Boolean {
         return oldItem.category.ordinal == newItem.category.ordinal
      }

      override fun areContentsTheSame(
         oldItem: TvShowsCategorized,
         newItem: TvShowsCategorized
      ): Boolean {
         return oldItem == newItem
      }
   }
}
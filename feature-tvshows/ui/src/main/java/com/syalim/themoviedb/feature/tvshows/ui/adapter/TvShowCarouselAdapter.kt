package com.syalim.themoviedb.feature.tvshows.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.BaseAdapter
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowCarouselBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class TvShowCarouselAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : BaseAdapter<TvShow, TvShowCarouselAdapter.TvShowCarouselViewHolder>(TvShowDataComparator) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowCarouselViewHolder {
      return TvShowCarouselViewHolder(parent.viewBinding(ItemMovieTvshowCarouselBinding::inflate))
   }

   override fun onBindViewHolder(holder: TvShowCarouselViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class TvShowCarouselViewHolder(private val binding: ItemMovieTvshowCarouselBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: TvShow) {
         customImageLoader
            .imageUrl(item.posterPath)
            .loadBackdropInto(binding.ivPoster)
         itemView.setOnClickListener {
            AppNavigator.navigateTo(
               NavParam(
                  destination = AppDestination.TV_SHOW_DETAIL,
                  args = buildArgs(NavArgsKey.TV_SHOW_ID to item.id.toString())
               )
            )
         }
      }
   }
}
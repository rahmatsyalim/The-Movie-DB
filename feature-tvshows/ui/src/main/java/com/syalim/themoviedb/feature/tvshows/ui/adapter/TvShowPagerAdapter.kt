package com.syalim.themoviedb.feature.tvshows.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class TvShowPagerAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : PagingDataAdapter<TvShow, TvShowPagerAdapter.TvShowPagerViewHolder>(TvShowDataComparator) {

   override fun onBindViewHolder(holder: TvShowPagerViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowPagerViewHolder {
      return TvShowPagerViewHolder(parent.viewBinding(ItemMovieTvshowBinding::inflate))
   }

   inner class TvShowPagerViewHolder(private val binding: ItemMovieTvshowBinding) :
      RecyclerView.ViewHolder(binding.root) {

      fun bind(item: TvShow) {
         binding.apply {
            tvTitle.text = item.nameWithYear
            customImageLoader
               .imageUrl(item.posterPath)
               .loadImageInto(ivPoster)
            ratingVoteAverage.rating = item.rating
            tvVoteCount.text = item.simpleVoteCount
         }
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
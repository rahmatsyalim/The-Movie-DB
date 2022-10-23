package com.syalim.themoviedb.feature.tvshows.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.BaseAdapter
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import javax.inject.Inject
import kotlin.random.Random


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
class TvShowAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : BaseAdapter<TvShow, TvShowAdapter.TvShowViewHolder>(TvShowDataComparator) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
      val itemBinding = parent.viewBinding(ItemMovieTvshowBinding::inflate)
      itemBinding.root.setWidthTo3itemsInRow(parent, 24)
      return TvShowViewHolder(itemBinding)
   }

   override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class TvShowViewHolder(private val binding: ItemMovieTvshowBinding) :
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
         binding.ivPoster.transitionName = item.id.toString() + Random.nextInt(100)
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
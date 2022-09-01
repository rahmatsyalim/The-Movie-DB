package com.syalim.themoviedb.presentation.main.discover

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemMovieBinding
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class DiscoverPagerAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : PagingDataAdapter<com.syalim.themoviedb.domain.movie.model.Movie, DiscoverPagerAdapter.DiscoverViewHolder>(
   DataComparator
) {

   override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
      return DiscoverViewHolder(parent.viewBinding(ItemMovieBinding::inflate))
   }

   var onItemClickListener: ((com.syalim.themoviedb.domain.movie.model.Movie) -> Unit)? = null

   inner class DiscoverViewHolder(private val binding: ItemMovieBinding) :
      RecyclerView.ViewHolder(binding.root) {

      fun bind(item: com.syalim.themoviedb.domain.movie.model.Movie) {
         binding.apply {
            tvTitle.text = item.titleWithYear
            coilImageLoader
               .imageUrl(item.posterPath)
               .loadPosterInto(ivPoster)
            ratingVoteAverage.rating = item.rating
            tvVoteCount.text = item.simpleVoteCount
         }
         itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
         }
      }
   }

   private object DataComparator : DiffUtil.ItemCallback<com.syalim.themoviedb.domain.movie.model.Movie>() {
      override fun areItemsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.Movie,
         newItem: com.syalim.themoviedb.domain.movie.model.Movie
      ): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.Movie,
         newItem: com.syalim.themoviedb.domain.movie.model.Movie
      ): Boolean {
         return oldItem == newItem
      }
   }

}
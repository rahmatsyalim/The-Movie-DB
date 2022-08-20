package com.syalim.themoviedb.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemMovieBinding
import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.presentation.common.CoilImageLoader
import com.syalim.themoviedb.presentation.common.CoilImageLoader.Companion.POSTER_THUMBNAIL
import com.syalim.themoviedb.presentation.common.utils.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class MoviesPagerAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : PagingDataAdapter<Movie, MoviesPagerAdapter.MoviesPagerViewHolder>(Comparator) {

   override fun onBindViewHolder(holder: MoviesPagerViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesPagerViewHolder {
      return MoviesPagerViewHolder(parent.viewBinding(ItemMovieBinding::inflate))
   }

   var onItemClickListener: ((Movie) -> Unit)? = null

   inner class MoviesPagerViewHolder(private val binding: ItemMovieBinding) :
      RecyclerView.ViewHolder(binding.root) {

      fun bind(item: Movie) {
         binding.apply {
            tvTitle.text = item.extras.titleWithYear
            coilImageLoader
               .imageUrl(item.posterPath, POSTER_THUMBNAIL)
               .loadInto(ivPoster)
            ratingVoteAverage.rating = item.extras.voteAverageRating
            tvVoteCount.text = item.extras.simpleVoteCount
         }
         itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
         }
      }
   }

   private object Comparator: DiffUtil.ItemCallback<Movie>() {
      override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
         return oldItem.id == newItem.id
      }
      override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
         return oldItem == newItem
      }
   }

}
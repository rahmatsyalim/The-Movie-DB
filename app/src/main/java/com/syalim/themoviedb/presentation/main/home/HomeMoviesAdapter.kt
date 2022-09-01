package com.syalim.themoviedb.presentation.main.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemMovieBinding
import com.syalim.themoviedb.presentation.utils.BaseAdapter
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
class HomeMoviesAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : BaseAdapter<com.syalim.themoviedb.domain.movie.model.Movie, HomeMoviesAdapter.MoviesViewHolder>(DataComparator) {

   var onItemClickListener: ((com.syalim.themoviedb.domain.movie.model.Movie) -> Unit)? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
      val itemBinding = parent.viewBinding(ItemMovieBinding::inflate)
      itemBinding.root.layoutParams.width = ((parent.measuredWidth * 0.33).toInt())
      return MoviesViewHolder(itemBinding)
   }

   override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
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
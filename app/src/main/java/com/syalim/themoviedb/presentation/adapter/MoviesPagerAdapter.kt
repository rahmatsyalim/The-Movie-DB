package com.syalim.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.databinding.ItemMovieBinding
import com.syalim.themoviedb.domain.model.MovieItemEntity


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MoviesPagerAdapter :
   PagingDataAdapter<MovieItemEntity, MoviesPagerAdapter.MoviesViewHolder>(MoviesComparator) {

   inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
      RecyclerView.ViewHolder(binding.root) {

      fun bind(item: MovieItemEntity) {
         binding.apply {
            tvTitle.text = item.originalTitle
            item.posterPath?.let {
               ivPoster.setImage(it.setImageUrl())
            }
         }
         itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
         }
      }
   }

   override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
      return MoviesViewHolder(
         ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
   }

   object MoviesComparator : DiffUtil.ItemCallback<MovieItemEntity>() {
      override fun areItemsTheSame(oldItem: MovieItemEntity, newItem: MovieItemEntity): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: MovieItemEntity, newItem: MovieItemEntity): Boolean {
         return oldItem == newItem
      }
   }

   private var onItemClickListener: ((MovieItemEntity) -> Unit)? = null

   fun onItemClickListener(listener: (MovieItemEntity) -> Unit) {
      onItemClickListener = listener
   }
}
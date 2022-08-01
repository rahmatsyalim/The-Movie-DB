package com.syalim.themoviedb.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemMovieBinding
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.presentation.common.extensions.dateGetYear
import com.syalim.themoviedb.presentation.common.extensions.loadImage
import com.syalim.themoviedb.utils.Constants.IMAGE_POSTER_THUMBNAIL_SIZE


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.HomeMoviesViewHolder>() {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMoviesViewHolder {
      val itemBinding = ItemMovieBinding.inflate(
         LayoutInflater.from(parent.context),
         parent,
         false
      )
      itemBinding.root.layoutParams.width = ((parent.measuredWidth * 0.33).toInt())
      return HomeMoviesViewHolder(itemBinding)
   }

   override fun onBindViewHolder(holder: HomeMoviesViewHolder, position: Int) {
      holder.bind(data.currentList[position])
   }

   override fun getItemCount(): Int {
      return data.currentList.size
   }

   inner class HomeMoviesViewHolder(private val binding: ItemMovieBinding) :
      RecyclerView.ViewHolder(binding.root) {

      @SuppressLint("SetTextI18n")
      fun bind(item: MovieItemEntity) {
         binding.apply {
            tvTitle.text = "${item.title} (${item.releaseDate.dateGetYear()})"
            ivPoster.loadImage(item.posterPath, IMAGE_POSTER_THUMBNAIL_SIZE)
         }
         itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
         }
      }
   }

   object HomeMoviesComparator : DiffUtil.ItemCallback<MovieItemEntity>() {
      override fun areItemsTheSame(oldItem: MovieItemEntity, newItem: MovieItemEntity): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: MovieItemEntity, newItem: MovieItemEntity): Boolean {
         return oldItem == newItem
      }
   }

   val data = AsyncListDiffer(this, HomeMoviesComparator)

   private var onItemClickListener: ((MovieItemEntity) -> Unit)? = null

   fun setOnItemClickListener(listener: (MovieItemEntity) -> Unit) {
      onItemClickListener = listener
   }

}
package com.syalim.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.common.dateGetYear
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.databinding.ItemMovieHomeBinding
import com.syalim.themoviedb.domain.model.MovieItemEntity


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
class HomeMoviesAdapter : RecyclerView.Adapter<HomeMoviesAdapter.HomeMoviesViewHolder>() {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMoviesViewHolder {
      return HomeMoviesViewHolder(
         ItemMovieHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
         )
      )
   }

   override fun onBindViewHolder(holder: HomeMoviesViewHolder, position: Int) {
      holder.bind(data.currentList[position])
   }

   override fun getItemCount(): Int {
      return data.currentList.size
   }

   inner class HomeMoviesViewHolder(private val binding: ItemMovieHomeBinding) :
      RecyclerView.ViewHolder(binding.root) {

      fun bind(item: MovieItemEntity) {
         binding.apply {
            tvTitle.text = "${item.title} (${item.releaseDate.dateGetYear()})"
            item.posterPath?.let {
               ivPoster.setImage(it.setImageUrl())
            }
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
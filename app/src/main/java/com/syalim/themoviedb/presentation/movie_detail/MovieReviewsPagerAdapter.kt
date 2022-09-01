package com.syalim.themoviedb.presentation.movie_detail

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemMovieReviewBinding
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/19
 * rahmatsyalim@gmail.com
 */
class MovieReviewsPagerAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) :
   PagingDataAdapter<com.syalim.themoviedb.domain.movie.model.MovieReview, MovieReviewsPagerAdapter.MovieReviewViewHolder>(
      DataComparator
   ) {

   override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
      return MovieReviewViewHolder(parent.viewBinding(ItemMovieReviewBinding::inflate))
   }

   private object DataComparator : DiffUtil.ItemCallback<com.syalim.themoviedb.domain.movie.model.MovieReview>() {
      override fun areItemsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.MovieReview,
         newItem: com.syalim.themoviedb.domain.movie.model.MovieReview
      ): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: com.syalim.themoviedb.domain.movie.model.MovieReview,
         newItem: com.syalim.themoviedb.domain.movie.model.MovieReview
      ): Boolean {
         return oldItem == newItem
      }
   }

   inner class MovieReviewViewHolder(private val binding: ItemMovieReviewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: com.syalim.themoviedb.domain.movie.model.MovieReview) {
         binding.apply {
            tvName.text = item.author
            tvComments.text = item.content
            coilImageLoader
               .imageUrl(item.avatarPath)
               .loadAvatarInto(ivPhoto)
         }
      }
   }
}
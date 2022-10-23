package com.syalim.themoviedb.feature.movies.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowReviewBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.domain.model.MovieReview
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/19
 * rahmatsyalim@gmail.com
 */
class MovieReviewsPagerAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : PagingDataAdapter<MovieReview, MovieReviewsPagerAdapter.MovieReviewViewHolder>(DataComparator) {

   override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder {
      return MovieReviewViewHolder(parent.viewBinding(ItemMovieTvshowReviewBinding::inflate))
   }

   inner class MovieReviewViewHolder(private val binding: ItemMovieTvshowReviewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: MovieReview) {
         binding.apply {
            tvName.text = item.author
            tvComments.text = item.content
            customImageLoader
               .imageUrl(item.avatarPath)
               .loadAvatarInto(ivPhoto)
         }
      }
   }

   private object DataComparator : DiffUtil.ItemCallback<MovieReview>() {
      override fun areItemsTheSame(
         oldItem: MovieReview,
         newItem: MovieReview
      ): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: MovieReview,
         newItem: MovieReview
      ): Boolean {
         return oldItem == newItem
      }
   }
}
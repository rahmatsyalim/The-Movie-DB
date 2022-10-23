package com.syalim.themoviedb.feature.movies.ui.adapter

import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.setCustomCLickListener
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class MoviePagerAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : PagingDataAdapter<Movie, MoviePagerAdapter.MoviePagerViewHolder>(MovieDataComparator) {

   override fun onBindViewHolder(holder: MoviePagerViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagerViewHolder {
      return MoviePagerViewHolder(parent.viewBinding(ItemMovieTvshowBinding::inflate))
   }

   inner class MoviePagerViewHolder(private val binding: ItemMovieTvshowBinding) :
      RecyclerView.ViewHolder(binding.root) {

      fun bind(item: Movie) {
         binding.apply {
            tvTitle.text = item.titleWithYear
            customImageLoader
               .imageUrl(item.posterPath)
               .loadImageInto(ivPoster)
            ratingVoteAverage.rating = item.rating
            tvVoteCount.text = item.simpleVoteCount
         }
         itemView.generateTransitionName(hashCode().toString())
         itemView.setCustomCLickListener {
            AppNavigator.navigateTo(
               NavParam(
                  destination = AppDestination.MOVIE_DETAIL,
                  args = buildArgs(
                     NavArgsKey.MOVIE_ID to item.id.toString(),
                     sendElementTransitionName(transitionName)
                  ),
                  navExtras = FragmentNavigatorExtras(sendSharedElement(this))
               )
            )
         }
      }
   }
}
package com.syalim.themoviedb.feature.movies.ui.adapter

import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.BaseAdapter
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowCarouselBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.setCustomCLickListener
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

class MovieCarouselAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : BaseAdapter<Movie, MovieCarouselAdapter.MovieCarouselViewHolder>(MovieDataComparator) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCarouselViewHolder {
      return MovieCarouselViewHolder(parent.viewBinding(ItemMovieTvshowCarouselBinding::inflate))
   }

   override fun onBindViewHolder(holder: MovieCarouselViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class MovieCarouselViewHolder(private val binding: ItemMovieTvshowCarouselBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: Movie) {
         customImageLoader
            .imageUrl(item.posterPath)
            .loadBackdropInto(binding.ivPoster)
         itemView.generateTransitionName(item.id.toString())
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
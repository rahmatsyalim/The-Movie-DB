package com.syalim.themoviedb.feature.movies.ui.adapter

import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.BaseAdapter
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.setCustomCLickListener
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
class MovieAdapter @Inject constructor(
   private val customImageLoader: CustomImageLoader
) : BaseAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDataComparator) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
      val itemBinding = parent.viewBinding(ItemMovieTvshowBinding::inflate)
      itemBinding.root.setWidthTo3itemsInRow(parent, 24)
      return MovieViewHolder(itemBinding)
   }

   override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class MovieViewHolder(private val binding: ItemMovieTvshowBinding) :
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
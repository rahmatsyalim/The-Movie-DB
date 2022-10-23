package com.syalim.themoviedb.feature.movies.ui.discover

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.syalim.themoviedb.core.ui.utils.addFilterChip
import com.syalim.themoviedb.core.ui.utils.isInvisible
import com.syalim.themoviedb.core.ui.utils.isVisible
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentDiscoverMoviesFilterBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/08/24
 * rahmatsyalim@gmail.com
 */

class DiscoverMoviesFilterFragment(private val viewModel: DiscoverMoviesViewModel) : BottomSheetDialogFragment() {

   private val binding: FragmentDiscoverMoviesFilterBinding by viewBinding()
   private lateinit var bottomSheetDialog: BottomSheetDialog
   private var selectedGenre: List<Int> = emptyList()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      collectGenre()
      binding.btnFilter.setOnClickListener {
         if (selectedGenre != viewModel.getSelectedGenre()) {
            viewModel.fetchDiscoverMovies(selectedGenre)
         }
         dismiss()
      }
   }

   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
      bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
      bottomSheetDialog.behavior.apply {
         skipCollapsed = true
         isFitToContents = false
         expandedOffset = resources.getDimension(com.syalim.themoviedb.core.ui.R.dimen.dimen_actionBar).toInt()
      }
      return bottomSheetDialog
   }

   private var collectGenreJob: Job? = null
   private fun collectGenre() {
      collectGenreJob?.cancel()
      collectGenreJob = viewLifecycleOwner.lifecycleScope.launch {
         viewModel.filterGenreState.collect { data ->
            data?.let { genres ->
               selectedGenre = viewModel.getSelectedGenre()
               for (chip in genres.associate { it.id to it.name }) {
                  addGenreChip(chip.key, chip.value, chip.key in selectedGenre)
               }
               listOf(
                  "Popularity Desc", "Popularity Asc", "Rating Desc",
                  "Rating Asc", "Release Date Desc", "Release Date Asc"
               ).forEachIndexed { index, s ->
                  addSortByChip(s, index == 0)
               }
            }
         }
      }
   }

   private fun btnFilterVisibility() {
      binding.btnFilter.let { if (selectedGenre != viewModel.getSelectedGenre()) it.isVisible() else it.isInvisible() }
   }

   private fun addSortByChip(name: String, setChecked: Boolean) {
      binding.chipGroupSort.addFilterChip(requireContext(), name, setChecked) { isChecked ->
         bottomSheetDialog.behavior.apply {
            if (state != BottomSheetBehavior.STATE_EXPANDED) state = BottomSheetBehavior.STATE_EXPANDED
         }
         if (isChecked) {

         }
      }
   }

   private fun addGenreChip(id: Int, name: String, setChecked: Boolean) {
      binding.chipGroupGenre.addFilterChip(requireContext(), name, setChecked) { isChecked ->
         bottomSheetDialog.behavior.apply {
            if (state != BottomSheetBehavior.STATE_EXPANDED) state = BottomSheetBehavior.STATE_EXPANDED
         }
         selectedGenre = if (isChecked) {
            selectedGenre.plus(id).sortedDescending()
         } else {
            selectedGenre.minus(id).sortedDescending()
         }
         btnFilterVisibility()
      }
   }

}
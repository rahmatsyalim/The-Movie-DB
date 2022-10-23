package com.syalim.themoviedb.core.ui.utils

import android.content.Context
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.syalim.themoviedb.core.ui.R


/**
 * Created by Rahmat Syalim on 2022/08/24
 * rahmatsyalim@gmail.com
 */

fun ChipGroup.addFilterChip(
   context: Context, textValue: String, checked: Boolean,
   onChecked: (isChecked: Boolean) -> Unit
) {
   val chip = Chip(context, null, R.attr.customFilterChip)
   chip.apply {
      text = textValue
      isChecked = checked
      setOnCheckedChangeListener { _, isChecked ->
         onChecked.invoke(isChecked)
      }
   }
   addView(chip)
}

fun ChipGroup.addStaticChip(context: Context, textValue: String) {
   val chip = Chip(context, null, R.attr.customStaticShip)
   chip.text = textValue
   addView(chip)
}
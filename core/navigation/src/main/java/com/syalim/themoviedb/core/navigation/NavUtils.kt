package com.syalim.themoviedb.core.navigation

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.navigation.NavigationBarView
import java.lang.ref.WeakReference


/**
 * Created by Rahmat Syalim on 2022/09/11
 * rahmatsyalim@gmail.com
 */

fun NavController.proceedNavigate(
   @IdRes id: Int,
   args: Bundle?,
   navOptions: NavOptions?,
   navExtras: Navigator.Extras?
) {
   navigate(id, args, navOptions, navExtras)
}

fun FragmentManager.getCurrentFragment(@IdRes navHostId: Int): Fragment? {
   return findFragmentById(navHostId)
      ?.childFragmentManager
      ?.fragments
      ?.first()
}

private fun NavDestination.matchDestinationId(@IdRes id: Int) = hierarchy.any { it.id == id }

fun NavigationBarView.customSetupWithNavController(
   navController: NavController,
   block: (() -> Unit)? = null
) {
   val builder = NavOptions.Builder()
      .setLaunchSingleTop(true)
      .setRestoreState(true)
   setOnItemSelectedListener { item ->
      if (item.order and Menu.CATEGORY_SECONDARY == 0) {
         builder.setPopUpTo(
            navController.graph.findStartDestination().id,
            inclusive = false,
            saveState = true
         )
      }
      val options = builder.build()
      try {
         block?.invoke()
         navController.navigate(item.itemId, null, options)
         navController.currentDestination?.matchDestinationId(item.itemId) == true
      } catch (e: IllegalArgumentException) {
         false
      }
   }
   val weakReference = WeakReference(this)
   navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
      override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
         val view = weakReference.get()
         if (view == null) {
            navController.removeOnDestinationChangedListener(this)
            return
         }
         controller.popBackStack(destination.id, inclusive = false, saveState = true)
         view.menu.forEach { item ->
            if (destination.matchDestinationId(item.itemId)) {
               item.isChecked = true
            }
         }
      }
   })
}

fun MaterialButtonToggleGroup.customSetupWithNavController(navController: NavController) {
   val weakReference = WeakReference(this)
   navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
      override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
         val view = weakReference.get()
         if (view == null) {
            navController.removeOnDestinationChangedListener(this)
            return
         }
         view.check(destination.id)
      }
   })
   addOnButtonCheckedListener { _, checkedId, isChecked ->
      if (isChecked) {
         val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
         navController.navigate(
            checkedId,
            null,
            builder.setPopUpTo(
               navController.graph.findStartDestination().id,
               inclusive = false,
               saveState = true
            ).build()
         )
      }
   }
}

fun buildArgs(vararg arg: Pair<String, Any?>): Bundle {
   return Bundle().apply {
      arg.forEach { (key, value) ->
         when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Parcelable -> putParcelable(key, value)
            is Enum<*> -> putInt(key, value.ordinal)
         }
      }
   }
}

fun View.generateTransitionName(identifier: String) {
   transitionName = "ELEMENT_TRANSITION_${identifier}"
}

fun Bundle?.getElementTransitionName(): String? = this?.getString("ELEMENT_TRANSITION_NAME")

fun Bundle?.getIntOrNull(key: String): Int? {
   return when (val int = this?.getInt(key)) {
      0 -> null
      else -> int
   }
}

inline fun <reified T : Enum<T>> Bundle.getEnum(key: String) = getInt(key).let { enumValues<T>()[it] }

fun sendElementTransitionName(name: String): Pair<String, String> =
   "ELEMENT_TRANSITION_NAME" to name

fun sendSharedElement(view: View): Pair<View, String> = view to view.transitionName
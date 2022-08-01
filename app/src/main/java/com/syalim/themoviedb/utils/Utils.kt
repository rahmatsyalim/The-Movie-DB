package com.syalim.themoviedb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.utils.Constants.NETWORK_ERROR_UNEXPECTED
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Rahmat Syalim on 2022/07/30
 * rahmatsyalim@gmail.com
 */
class Utils {

   companion object {

      fun internetIsAvailable(context: Context): Boolean {
         val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
         ) as ConnectivityManager
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
               connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
               capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
               else -> false
            }
         } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false
            return when (activeNetworkInfo.type) {
               ConnectivityManager.TYPE_WIFI -> true
               ConnectivityManager.TYPE_MOBILE -> true
               else -> false
            }
         }
      }

      fun getHttpErrorMessage(exception: HttpException): String {
         val errorBody = exception.response()?.errorBody()?.string()
         return try {
            JSONObject(errorBody!!).getString("status_message")
         } catch (e: Exception) {
            NETWORK_ERROR_UNEXPECTED
         }
      }

      fun <T : Any, VH : RecyclerView.ViewHolder> handlePagingLoadState(
         adapter: PagingDataAdapter<T, VH>,
         onFirstLoading: ((Boolean) -> Unit)? = null,
         onLoading: ((Boolean) -> Unit)? = null,
         onError: ((String) -> Unit)? = null,
         onEmpty: (() -> Unit)? = null,
         onLoaded: (() -> Unit)? = null
      ) {
         adapter.addLoadStateListener { loadState ->
            with(loadState) {
               onFirstLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount == 0)

               onLoading?.invoke(refresh is LoadState.Loading && adapter.itemCount > 0)

               val errorState =
                  source.append as? LoadState.Error
                     ?: source.prepend as? LoadState.Error
                     ?: source.refresh as? LoadState.Error

               errorState?.error?.let { e ->
                  when (e) {
                     is IOException -> e.message?.let { onError?.invoke(Constants.NETWORK_ERROR_IO_EXCEPTION) }
                     is HttpException -> onError?.invoke(getHttpErrorMessage(e))
                     else -> e.message?.let { onError?.invoke(it) }
                  }
               }

               if (source.append is LoadState.NotLoading
                  && source.append.endOfPaginationReached
                  && adapter.itemCount == 0
               ) {
                  onEmpty?.invoke()
               }
               if (source.refresh is LoadState.NotLoading
                  && adapter.itemCount > 0
               ) {
                  onLoaded?.invoke()
               }
            }
         }
      }


   }
}
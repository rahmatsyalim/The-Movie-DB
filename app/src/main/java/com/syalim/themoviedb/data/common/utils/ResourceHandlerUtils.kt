package com.syalim.themoviedb.data.common.utils

import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.AppExceptions
import com.syalim.themoviedb.common.Constants.CACHE_EXPIRED_TIME
import com.syalim.themoviedb.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

inline fun <T> resourceStream(crossinline block: suspend () -> Resource<T>): Flow<Resource<T>> {
   return flow { emit(block.invoke()) }
      .onStart { emit(Resource.Loading()) }
      .catch { emit(Resource.Error(it.getException)) }
}

inline fun <Local, Remote, R> cachedResourceStream(
   crossinline fetchLocal: suspend () -> Local,
   crossinline fetchRemote: suspend () -> Remote,
   crossinline clearLocal: suspend () -> Unit,
   crossinline insertLocal: suspend Remote.() -> Unit,
   crossinline mapToResult: Local.() -> R,
   crossinline lastCachedTime: Local.() -> Long,
   crossinline isEmpty: Local.() -> Boolean
): Flow<Resource<R>> {
   return flow {
      val timeNow = System.currentTimeMillis()
      val expired = TimeUnit.MINUTES.toMillis(CACHE_EXPIRED_TIME)
      var currentResult: Local? = null
      try {
         fetchLocal.invoke().let { localCache ->
            emit(Resource.Loading(localCache.mapToResult()))
            currentResult = localCache
            if (isEmpty(localCache)) {
               val remote = fetchRemote()
               insertLocal(remote)
               currentResult = null
            } else if ((timeNow - (lastCachedTime(localCache))) > expired) {
               val remote = fetchRemote()
               clearLocal()
               insertLocal(remote)
               currentResult = null
            }
         }
      } catch (e: Exception) {
         emit(Resource.Error(e.getException))
      } finally {
         if (currentResult == null) {
            currentResult = fetchLocal.invoke()
         }
         currentResult?.let {
            emit(Resource.Success(it.mapToResult(), it.isEmpty()))
         }
      }
   }.onStart { emit(Resource.Loading()) }
}

val Throwable.getException: Throwable
   get() = run {
      return when (this) {
         is HttpException -> AppExceptions.Http(errorMessage)
         is IOException -> AppExceptions.IoOperation(R.string.error_net_io_operation, message)
         else -> this
      }
   }

val HttpException.errorMessage: String
   get() = run {
      val errorBody = response()?.errorBody()?.string()
      return try {
         JSONObject(errorBody!!).getString("status_message")
      } catch (e: Exception) {
         e.message ?: "Unknown Error."
      }
   }

package com.syalim.themoviedb.core.network.utils

import com.syalim.themoviedb.core.common.AppException
import com.syalim.themoviedb.core.common.Constants.CACHE_EXPIRED_TIME
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.core.network.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

inline fun <T> resourceStream(crossinline block: suspend () -> Result<T>): Flow<Result<T>> {
   return flow {
      try {
         emit(block.invoke())
      } catch (e: HttpException) {
         emit(Result.Failure(e.getException()))
      } catch (e: IOException) {
         emit(Result.Failure(AppException.StringResMsg(R.string.could_not_reach_server)))
      }
   }
}

inline fun <REMOTE, LOCAL, DOMAIN> cachedResourceStream(
   crossinline fetchLocal: suspend () -> LOCAL,
   crossinline fetchRemote: suspend () -> REMOTE,
   crossinline cacheData: suspend REMOTE.() -> Unit,
   crossinline isNoData: suspend LOCAL.() -> Boolean,
   crossinline lastCachedTime: LOCAL.() -> Long,
   crossinline mapToResult: LOCAL.() -> DOMAIN
): Flow<Result<DOMAIN>> = flow {
   val timeNow = System.currentTimeMillis()
   val expired = TimeUnit.MINUTES.toMillis(CACHE_EXPIRED_TIME)
   val cached = fetchLocal.invoke()
   val result =
      if (isNoData(cached) || timeNow - lastCachedTime(cached) > expired) {
         try {
            cacheData.invoke(fetchRemote.invoke())
            Result.Success(fetchLocal.invoke().mapToResult())
         } catch (e: HttpException) {
            Result.Failure(e.getException())
         } catch (e: IOException) {
            Result.Failure(AppException.StringResMsg(R.string.could_not_reach_server))
         }
      } else {
         Result.Success(cached.mapToResult())
      }
   emit(result)
}

fun HttpException.getException(): Exception {
   val errorBody = response()?.errorBody()?.string()
   return try {
      val message = JSONObject(errorBody!!).getString("status_message") + " (Code: ${code()})"
      AppException.StringMsg(message)
   } catch (e: Exception) {
      e
   }
}

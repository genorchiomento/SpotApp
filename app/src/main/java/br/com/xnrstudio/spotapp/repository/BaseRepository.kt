package br.com.xnrstudio.spotapp.repository

import br.com.xnrstudio.spotapp.repository.api.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
  suspend fun <T> apiCall(
    apiCall: suspend () -> T
  ): Resource<T> {
    return withContext(Dispatchers.IO) {
      try {
        Resource.Success(apiCall.invoke())
      } catch (throwable: Throwable) {
        when (throwable) {
          is HttpException -> {
            Resource.Failure(
              isNetworkError = false,
              errorCode = throwable.code(),
              errorBody = throwable.response()?.errorBody()
            )
          }
          else -> {
            Resource.Failure(
              isNetworkError = true,
              errorCode = null,
              errorBody = null
            )
          }
        }
      }
    }
  }
}

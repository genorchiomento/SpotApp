package br.com.xnrstudio.spotapp.repository.api

import br.com.xnrstudio.spotapp.repository.api.service.SpotLoginService
import br.com.xnrstudio.spotapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InitializeRetrofit {

  private val logging = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
  }

  private val client = OkHttpClient
    .Builder()
    .addInterceptor(logging)
    .build()

  private val retrofit = Retrofit
    .Builder()
    .baseUrl(Constants.BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  fun apiService(): SpotLoginService = retrofit.create(
    SpotLoginService::class.java
  )
}

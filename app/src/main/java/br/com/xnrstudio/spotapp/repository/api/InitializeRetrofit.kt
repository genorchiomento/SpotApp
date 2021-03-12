package br.com.xnrstudio.spotapp.repository.api

import br.com.xnrstudio.spotapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InitializeRetrofit {

  private val logging = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
  }

  fun <Api> buildApi(
    api: Class<Api>,
    token: String? = null
  ): Api {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(
        OkHttpClient.Builder()
          .addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder().also {
              it.addHeader("Authorization", "Bearer $token")
            }.build())
          }.also { client ->
            client.addInterceptor(logging)
          }.build()
      )
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(api)
  }
}

package br.com.xnrstudio.spotapp.repository.api.service

import br.com.xnrstudio.spotapp.model.SpotLoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotLoginService {

  @FormUrlEncoded
  @POST("login")
  suspend fun doLogin(
    @Field("username") username: String,
    @Field("password") password: String
  ): SpotLoginResponse
}

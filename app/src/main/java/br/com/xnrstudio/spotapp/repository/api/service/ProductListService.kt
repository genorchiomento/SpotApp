package br.com.xnrstudio.spotapp.repository.api.service

import br.com.xnrstudio.spotapp.model.ProductResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ProductListService {

  @FormUrlEncoded
  @GET("produtos")
  suspend fun getProductList(
    @Field("Authorization") token: String?
  ): ProductResponse
}

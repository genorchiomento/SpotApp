package br.com.xnrstudio.spotapp.repository.api.service

import br.com.xnrstudio.spotapp.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductListService {

  @GET("produtos")
  suspend fun getProductList(
    @Header("Authorization") token: String?
  ): ProductResponse
}

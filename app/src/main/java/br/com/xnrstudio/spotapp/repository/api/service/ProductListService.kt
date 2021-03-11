package br.com.xnrstudio.spotapp.repository.api.service

import br.com.xnrstudio.spotapp.model.ProductResponse
import retrofit2.http.GET

interface ProductListService {

  @GET("produtos")
  suspend fun getProductList(): ProductResponse
}

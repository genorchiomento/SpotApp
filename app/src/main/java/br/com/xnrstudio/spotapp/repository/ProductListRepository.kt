package br.com.xnrstudio.spotapp.repository

import br.com.xnrstudio.spotapp.repository.api.service.ProductListService

class ProductListRepository(
  private val productApi: ProductListService
) : BaseRepository() {

  suspend fun getProductList() = apiCall { productApi.getProductList() }
}

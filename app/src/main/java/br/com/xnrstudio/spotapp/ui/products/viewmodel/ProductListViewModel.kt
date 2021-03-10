package br.com.xnrstudio.spotapp.ui.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.xnrstudio.spotapp.model.ProductResponse
import br.com.xnrstudio.spotapp.repository.ProductListRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import kotlinx.coroutines.launch

class ProductListViewModel(
  private val repository: ProductListRepository
) : ViewModel() {
  private val productListMutableLiveData: MutableLiveData<Resource<ProductResponse>> =
    MutableLiveData()
  val productList: LiveData<Resource<ProductResponse>>
    get() = productListMutableLiveData

  fun getProductList(
    token: String?
  ) = viewModelScope.launch {
    productListMutableLiveData.value = Resource.Loading
    productListMutableLiveData.value = repository.getProductList(token)
  }
}

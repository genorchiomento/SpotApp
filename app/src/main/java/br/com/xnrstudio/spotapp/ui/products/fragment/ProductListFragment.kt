package br.com.xnrstudio.spotapp.ui.products.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.com.xnrstudio.spotapp.databinding.FragmentProductListBinding
import br.com.xnrstudio.spotapp.repository.ProductListRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import br.com.xnrstudio.spotapp.ui.BaseFragment
import br.com.xnrstudio.spotapp.ui.products.viewmodel.ProductListViewModel
import br.com.xnrstudio.spotapp.util.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProductListFragment :
  BaseFragment<ProductListViewModel, FragmentProductListBinding, ProductListRepository>() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.progressBarList.visible(false)

    val token = runBlocking { userPreferences.token.first() }
    viewModel.getProductList(token)

    viewModel.productList.observe(viewLifecycleOwner, Observer {
      when (it) {
        is Resource.Success -> {
          binding.progressBarList.visible(false)
          updateList("aaaaaaaaaxxx")
        }

        is Resource.Loading -> {
          binding.progressBarList.visible(true)
        }
      }
    })
  }

  private fun updateList(productList: String) {
    with(binding) {
      tvMarcaProduto.text = productList
    }
  }

  override fun getViewModel() = ProductListViewModel::class.java

  override fun getFragmentViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ) = FragmentProductListBinding.inflate(
    inflater,
    container,
    false
  )

  override fun getFragmentRepository(): ProductListRepository {
    val token = runBlocking { userPreferences.token.first() }
    val api = initRetrofit.productService(token)
    return ProductListRepository(api)
  }
}

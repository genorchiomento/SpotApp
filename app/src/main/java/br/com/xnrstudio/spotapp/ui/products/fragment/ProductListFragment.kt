package br.com.xnrstudio.spotapp.ui.products.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.xnrstudio.spotapp.databinding.FragmentProductListBinding
import br.com.xnrstudio.spotapp.model.ProductResponse
import br.com.xnrstudio.spotapp.repository.ProductListRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import br.com.xnrstudio.spotapp.repository.api.service.ProductListService
import br.com.xnrstudio.spotapp.ui.BaseFragment
import br.com.xnrstudio.spotapp.ui.products.adapter.ProductListAdapter
import br.com.xnrstudio.spotapp.ui.products.viewmodel.ProductListViewModel
import br.com.xnrstudio.spotapp.util.handleApiError
import br.com.xnrstudio.spotapp.util.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProductListFragment :
  BaseFragment<ProductListViewModel, FragmentProductListBinding, ProductListRepository>() {

  private lateinit var productAdapter: ProductListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.progressBarList.visible(false)

    viewModel.getProductList()

    viewModel.productList.observe(viewLifecycleOwner, Observer { product ->
      when (product) {
        is Resource.Success -> {
          binding.progressBarList.visible(false)
          updateList(product.value)

          productAdapter.notifyDataSetChanged()
        }

        is Resource.Loading -> {
          binding.progressBarList.visible(true)
        }

        is Resource.Failure -> handleApiError(product)
      }
    })
  }

  private fun updateList(productList: ProductResponse) = with(binding) {
    initRecycler()
    productAdapter.setProductList(productList)
    productAdapter.notifyDataSetChanged()
  }

  private fun initRecycler() {
    binding.rvProductList.apply {
      layoutManager = LinearLayoutManager(requireContext())
      productAdapter = ProductListAdapter()
      adapter = productAdapter
    }
  }

//  fun setProducts() {
//    val item = ArrayList<String>()
//    item.add("marca 1")
//    item.add("marca 2")
//    item.add("marca 3")
//    item.add("marca 4")
//    item.add("marca 5")
//
//    productAdapter.setProductList(item)
//    productAdapter.notifyDataSetChanged()
//  }

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
    val api = initRetrofit.buildApi(ProductListService::class.java, token)
    return ProductListRepository(api)
  }
}

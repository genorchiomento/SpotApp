package br.com.xnrstudio.spotapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.xnrstudio.spotapp.repository.AuthRepository
import br.com.xnrstudio.spotapp.repository.BaseRepository
import br.com.xnrstudio.spotapp.repository.ProductListRepository
import br.com.xnrstudio.spotapp.ui.auth.AuthViewModel
import br.com.xnrstudio.spotapp.ui.products.ProductListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
  private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
      modelClass.isAssignableFrom(ProductListViewModel::class.java) -> ProductListViewModel(
        repository as ProductListRepository
      ) as T
      else -> throw IllegalArgumentException("ViewModel not found!")
    }
  }
}

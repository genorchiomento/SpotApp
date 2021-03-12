package br.com.xnrstudio.spotapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import br.com.xnrstudio.spotapp.UserPreferences
import br.com.xnrstudio.spotapp.repository.BaseRepository
import br.com.xnrstudio.spotapp.repository.api.InitializeRetrofit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : ViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

  protected lateinit var binding: B
  protected lateinit var viewModel: VM
  protected val initRetrofit = InitializeRetrofit()
  protected lateinit var userPreferences: UserPreferences

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    userPreferences = UserPreferences(requireContext())
    binding = getFragmentViewBinding(inflater, container)
    val factory = ViewModelFactory(getFragmentRepository())
    viewModel = ViewModelProvider(this, factory).get(getViewModel())
    lifecycleScope.launch { userPreferences.token.first() }
    return binding.root
  }

  abstract fun getViewModel(): Class<VM>

  abstract fun getFragmentViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

  abstract fun getFragmentRepository(): R
}

package br.com.xnrstudio.spotapp.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import br.com.xnrstudio.spotapp.databinding.FragmentLoginBinding
import br.com.xnrstudio.spotapp.repository.AuthRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import br.com.xnrstudio.spotapp.repository.api.service.SpotLoginService
import br.com.xnrstudio.spotapp.ui.BaseFragment
import br.com.xnrstudio.spotapp.ui.auth.viewmodel.AuthViewModel
import br.com.xnrstudio.spotapp.ui.products.activity.ProductListActivity
import br.com.xnrstudio.spotapp.util.enable
import br.com.xnrstudio.spotapp.util.handleApiError
import br.com.xnrstudio.spotapp.util.startNewActivity
import br.com.xnrstudio.spotapp.util.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.progressBarLogin.visible(false)
    binding.btnLogin.enable(false)

    viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
      binding.progressBarLogin.visible(it is Resource.Loading)

      when (it) {
        is Resource.Success -> {
          lifecycleScope.launch {
            it.value.token?.let { token -> viewModel.saveToken(token) }
            requireActivity().startNewActivity(ProductListActivity::class.java)
          }
        }

        is Resource.Failure -> handleApiError(it)
      }
    })

    binding.etFormPasswordLogin.addTextChangedListener {
      val username = binding.etFormUsernameLogin.text.toString().trim()
      binding.btnLogin.enable(username.isNotEmpty() && it.toString().isNotEmpty())
    }

    binding.btnLogin.setOnClickListener {
      val username = binding.etFormUsernameLogin.text.toString().trim()
      val password = binding.etFormPasswordLogin.text.toString().trim()
      viewModel.login(username, password)
    }
  }

  override fun getViewModel() = AuthViewModel::class.java

  override fun getFragmentViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ) = FragmentLoginBinding.inflate(
    inflater,
    container,
    false
  )

  override fun getFragmentRepository() =
    AuthRepository(initRetrofit.buildApi(SpotLoginService::class.java), userPreferences)
}

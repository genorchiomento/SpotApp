package br.com.xnrstudio.spotapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import br.com.xnrstudio.spotapp.databinding.FragmentLoginBinding
import br.com.xnrstudio.spotapp.repository.AuthRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import br.com.xnrstudio.spotapp.repository.api.service.SpotLoginService
import br.com.xnrstudio.spotapp.ui.BaseFragment
import br.com.xnrstudio.spotapp.ui.products.ProductListActivity
import br.com.xnrstudio.spotapp.util.enable
import br.com.xnrstudio.spotapp.util.handleApiError
import br.com.xnrstudio.spotapp.util.startNewActivity
import br.com.xnrstudio.spotapp.util.visible
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.progressBarLogin.visible(false)
    binding.btnLogin.enable(false)

    viewModel.loginResponse.observe(viewLifecycleOwner, { bodyApi ->
      binding.progressBarLogin.visible(bodyApi is Resource.Loading)

      when (bodyApi) {
        is Resource.Success -> {
          lifecycleScope.launch {
            bodyApi.value.token?.let { token -> viewModel.saveToken(token) }
            //TODO: REMOVER LOGICA DE RETORNO 200 MESMO QUANDO DEVERIA SER UM RETORNO 401
            if (!bodyApi.value.error) {
              requireActivity().startNewActivity(ProductListActivity::class.java)
            } else {
              Snackbar.make(requireView(), bodyApi.value.msg, Snackbar.LENGTH_LONG).show()
            }
          }
        }

        is Resource.Failure -> handleApiError(bodyApi)
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

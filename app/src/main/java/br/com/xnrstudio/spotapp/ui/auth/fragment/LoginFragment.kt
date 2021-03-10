package br.com.xnrstudio.spotapp.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import br.com.xnrstudio.spotapp.databinding.FragmentLoginBinding
import br.com.xnrstudio.spotapp.repository.AuthRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import br.com.xnrstudio.spotapp.ui.BaseFragment
import br.com.xnrstudio.spotapp.ui.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
      when (it) {
        is Resource.Success -> {
          lifecycleScope.launch {
            userPreferences.saveToken(it.value.token)
          }
        }

        is Resource.Failure -> {
          Toast.makeText(requireContext(), "Falha no login", Toast.LENGTH_LONG).show()
        }
      }
    })

    binding.btnLogin.setOnClickListener {
      val username = binding.etFormUsernameLogin.text.toString().trim()
      val password = binding.etFormPasswordLogin.text.toString().trim()
      //TODO fazer validacoes
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

  override fun getFragmentRepository() = AuthRepository(initRetrofit.apiService())


}

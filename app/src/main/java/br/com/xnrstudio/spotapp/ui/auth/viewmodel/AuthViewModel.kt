package br.com.xnrstudio.spotapp.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.xnrstudio.spotapp.model.SpotLoginResponse
import br.com.xnrstudio.spotapp.repository.AuthRepository
import br.com.xnrstudio.spotapp.repository.api.Resource
import kotlinx.coroutines.launch

class AuthViewModel(
  private val repository: AuthRepository
) : ViewModel() {

  private val loginMutableLiveData: MutableLiveData<Resource<SpotLoginResponse>> = MutableLiveData()
  val loginResponse: LiveData<Resource<SpotLoginResponse>>
    get() = loginMutableLiveData

  fun login(
    username: String,
    password: String
  ) = viewModelScope.launch {
    loginMutableLiveData.value = Resource.Loading
    loginMutableLiveData.value = repository.login(username, password)
  }

  suspend fun saveToken(token: String) {
    repository.saveToken(token)
  }
}

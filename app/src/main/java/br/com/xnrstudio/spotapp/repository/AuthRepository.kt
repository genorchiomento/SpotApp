package br.com.xnrstudio.spotapp.repository

import br.com.xnrstudio.spotapp.repository.api.service.SpotLoginService

class AuthRepository(
  private val api: SpotLoginService
) : BaseRepository() {

  suspend fun login(
    username: String,
    password: String
  ) = apiCall {
    api.doLogin(username, password)
  }

}

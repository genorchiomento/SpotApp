package br.com.xnrstudio.spotapp.repository

import br.com.xnrstudio.spotapp.UserPreferences
import br.com.xnrstudio.spotapp.repository.api.service.SpotLoginService

class AuthRepository(
  private val api: SpotLoginService,
  private val preferences: UserPreferences
) : BaseRepository() {

  suspend fun login(
    username: String,
    password: String
  ) = apiCall {
    api.doLogin(username, password)
  }

  suspend fun saveToken(token: String) {
    preferences.saveToken(token)
  }

}

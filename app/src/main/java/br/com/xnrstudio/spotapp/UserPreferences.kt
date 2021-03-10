package br.com.xnrstudio.spotapp

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
  context: Context
) {
  private val applicationContext = context.applicationContext
  private val dataStore: DataStore<Preferences> = applicationContext.createDataStore("data_store")

  val token: Flow<String?>
    get() = dataStore.data.map { preferences ->
      preferences[TOKEN_AUTH]
    }

  suspend fun saveToken(token: String) {
    dataStore.edit { preferences ->
      preferences[TOKEN_AUTH] = token
    }
  }

  companion object {
    private val TOKEN_AUTH = preferencesKey<String>("token_auth")
  }
}

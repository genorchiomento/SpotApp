package br.com.xnrstudio.spotapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import br.com.xnrstudio.spotapp.ui.auth.activity.AuthActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val userPreferences = UserPreferences(this)

    userPreferences.token.asLiveData().observe(this, Observer {
      Toast.makeText(this, it ?: "Falha no login", Toast.LENGTH_LONG).show()
      startActivity(Intent(this, AuthActivity::class.java))
    })
  }
}

package br.com.xnrstudio.spotapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import br.com.xnrstudio.spotapp.R
import br.com.xnrstudio.spotapp.UserPreferences
import br.com.xnrstudio.spotapp.ui.auth.AuthActivity
import br.com.xnrstudio.spotapp.ui.products.ProductListActivity
import br.com.xnrstudio.spotapp.util.startNewActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val userPreferences = UserPreferences(this)

    userPreferences.token.asLiveData().observe(this, {
      val activity = if (it == null) AuthActivity::class.java else ProductListActivity::class.java
      startNewActivity(activity)
    })
  }
}

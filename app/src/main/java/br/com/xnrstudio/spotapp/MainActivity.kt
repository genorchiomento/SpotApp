package br.com.xnrstudio.spotapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import br.com.xnrstudio.spotapp.ui.auth.activity.AuthActivity
import br.com.xnrstudio.spotapp.ui.products.activity.ProductListActivity
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

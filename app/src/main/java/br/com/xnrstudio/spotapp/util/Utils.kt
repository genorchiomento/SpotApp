package br.com.xnrstudio.spotapp.util

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import br.com.xnrstudio.spotapp.R
import br.com.xnrstudio.spotapp.repository.api.Resource
import br.com.xnrstudio.spotapp.ui.auth.fragment.LoginFragment
import com.google.android.material.snackbar.Snackbar

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
  Intent(this, activity).also {
    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(it)
  }
}

fun View.visible(isVisible: Boolean) {
  visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
  isEnabled = enabled
  alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(msg: String, action: (() -> Unit)? = null) {
  val snackbar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
  action?.let {
    snackbar.setAction(R.string.errorSnackbar) {
      it()
    }
  }
  snackbar.show()
}

fun Fragment.handleApiError(
  failure: Resource.Failure,
  retry: (() -> Unit)? = null
) {
  when {
    failure.isNetworkError -> requireView().snackbar(
      getString(R.string.errorNetworkCheckAgain),
      retry
    )
    //TODO: VERIFICAR SE BACKEND TRATOU O ERRO DE MOSTRAR 200 AO INVES DE 401
    failure.errorCode == 401 -> {
      if (this is LoginFragment) {
        requireView().snackbar(getString(R.string.errorAuth))
      }
    }
    else -> {
      val error = failure.errorBody?.string().toString()
      requireView().snackbar(error)
    }
  }
}

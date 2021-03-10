package br.com.xnrstudio.spotapp.model

import com.google.gson.annotations.SerializedName

data class SpotLoginResponse(
  @SerializedName("error")
  val error: Boolean,
  @SerializedName("msg")
  val msg: String,
  @SerializedName("profile")
  val profile: Int,
  @SerializedName("token")
  val token: String,
  @SerializedName("userId")
  val userId: Int
)

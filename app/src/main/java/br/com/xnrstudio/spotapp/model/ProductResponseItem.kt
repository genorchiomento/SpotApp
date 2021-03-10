package br.com.xnrstudio.spotapp.model


import com.google.gson.annotations.SerializedName

data class ProductResponseItem(
  @SerializedName("codBu")
  val codBu: String,
  @SerializedName("codCategoria")
  val codCategoria: String,
  @SerializedName("codMarca")
  val codMarca: String,
  @SerializedName("codSku")
  val codSku: String,
  @SerializedName("codSubBu")
  val codSubBu: String,
  @SerializedName("codSubCategoria")
  val codSubCategoria: String,
  @SerializedName("desBu")
  val desBu: String,
  @SerializedName("desCategoria")
  val desCategoria: String,
  @SerializedName("desMarca")
  val desMarca: String,
  @SerializedName("desSku")
  val desSku: String,
  @SerializedName("desSubBu")
  val desSubBu: String,
  @SerializedName("desSubCategoria")
  val desSubCategoria: String,
  @SerializedName("flConcorrente")
  val flConcorrente: String,
  @SerializedName("precoSugerido")
  val precoSugerido: String,
  @SerializedName("urlProduto")
  val urlProduto: String
)

package br.com.xnrstudio.spotapp.ui.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.xnrstudio.spotapp.R
import br.com.xnrstudio.spotapp.model.ProductResponseItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

  private var items = ArrayList<ProductResponseItem>()

  fun setProductList(product: ArrayList<ProductResponseItem>) {
    this.items = product
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ProductViewHolder {
    val inflater = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)

    return ProductViewHolder(inflater)
  }

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int = items.size

  class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val marca = view.tvDesMarca
    private val sku = view.tvDesSku
    private val price = view.tvPriceProduct
    private val icon = view.icProduct

    fun bind(product: ProductResponseItem) {
      val precoSugerido = product.precoSugerido.toDouble()
      val ptBr = Locale("pt", "BR")
      val priceProduct = NumberFormat.getCurrencyInstance(ptBr).format(precoSugerido)

      marca.text = product.desMarca
      sku.text = product.desSku
      price.text = priceProduct

      val url = product.urlProduto
      Glide.with(icon)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_image_placeholder)
        .error(R.drawable.ic_image_placeholder)
        .fallback(R.drawable.ic_image_placeholder)
        .into(icon)
    }
  }
}

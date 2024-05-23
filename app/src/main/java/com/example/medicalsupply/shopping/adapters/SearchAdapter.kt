package com.example.medicalsupply.shopping.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medicalsupply.databinding.ItemProductRvBinding
import com.example.medicalsupply.models.Product
import com.example.medicalsupply.utils.getProductPrice

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    lateinit var productList :ArrayList<Product>

    fun updateAdapter(newList:ArrayList<Product>){
       this.productList =newList
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: ItemProductRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(imgProduct)
                product.offerPercentage?.let {
                    val priceAfterOffer = it.getProductPrice(product.price)
                    tvNewPrice.text = "EGP ${String.format("%.2f", priceAfterOffer)}"
                    tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                if (product.offerPercentage == null) {
                    tvNewPrice.text = "EGP ${product.price}"
                    tvPrice.visibility = View.GONE
                }
                tvPrice.text = "EGP ${product.price}"
                tvName.text = product.name


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemProductRvBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }

    }

    var onClick: ((Product) -> Unit)? = null


}
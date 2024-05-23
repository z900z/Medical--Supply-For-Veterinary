package com.example.medicalsupply.shopping.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.medicalsupply.databinding.ItemBestDealsRvBinding
import com.example.medicalsupply.models.Product

class BestDealsAdapter : RecyclerView.Adapter<BestDealsAdapter.BestDealsHolder>() {

    inner class BestDealsHolder(private val binding: ItemBestDealsRvBinding) :
        ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(imgBestDeal)
                product.offerPercentage?.let {
                    val discountNum = (it/100)*product.price
                    val priceAfterOffer = product.price - discountNum
                    tvNewPrice.text = "EGP ${String.format("%.2f",priceAfterOffer)}"
                    tvOldPrice.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
                }
                if (product.offerPercentage==null)
                    tvNewPrice.visibility= View.INVISIBLE
                tvOldPrice.text="EGP ${product.price}"
                tvDealProductName.text = product.name
            }
        }
    }

    private val diffCallable = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallable)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsHolder {
        return BestDealsHolder(
            ItemBestDealsRvBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BestDealsHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }

    }

    var onClick:((Product)->Unit)?=null


}
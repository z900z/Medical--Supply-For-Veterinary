package com.example.medicalsupply.shopping.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.ItemViewpagerImageBinding

class VpImagesAdapter : RecyclerView.Adapter<VpImagesAdapter.VpImagesViewHolder>() {

    inner class VpImagesViewHolder(val binding: ItemViewpagerImageBinding) :
        ViewHolder(binding.root) {
     fun bind(imagePath:String){
         Glide.with(itemView).load(imagePath).placeholder(R.drawable.ic_clock) .into(binding.imageProductDetails)
     }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpImagesViewHolder {
        return VpImagesViewHolder(
            ItemViewpagerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VpImagesViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }
}
/*
package com.example.medicalsupply.product.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.medicalsupply.databinding.ItemReviewBinding
import com.example.medicalsupply.models.ModelDevices
import com.example.medicalsupply.product.models.UserReview


class ListUserReviewAdapter(private val listUserReview: List<UserReview>) :
    RecyclerView.Adapter<ListUserReviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUserReview[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listUserReview.size

    inner class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserReview) {
            binding.rbUserRate.rating = item.rate.toFloat()
            binding.tvUserName.text = item.user
            binding.tvUserReview.text = item.review

        }

    }


}*/

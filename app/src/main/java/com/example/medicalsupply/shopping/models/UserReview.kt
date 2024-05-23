package com.example.medicalsupply.shopping.models

data class UserReview(
    val user: String,
    val rate: Int,
    val review: String? = null
)

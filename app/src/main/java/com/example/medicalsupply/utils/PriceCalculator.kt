package com.example.medicalsupply.utils

import com.example.medicalsupply.models.Product

fun Float?.getProductPrice(price: Float): Float {
  // this --> percentage
    if (this==null)
        return price

    val discountNum = (this/100)* price
    val priceAfterOffer = price - discountNum

    return priceAfterOffer
}
package com.example.medicalsupply.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class ModelProduct(
    var category: String = "",
    var description: String = "",
    @PrimaryKey
    var id: String = "",
    var imageUrl: String = "",
    var name: String = "",
    var price: String = ""
)
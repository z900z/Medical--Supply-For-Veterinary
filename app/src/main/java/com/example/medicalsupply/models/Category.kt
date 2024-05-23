package com.example.medicalsupply.models

sealed class Category(val category: String) {
    object PetFood : Category("pet food")
    object Vaccines : Category("vaccines")
    object Hormones : Category("hormones")


}
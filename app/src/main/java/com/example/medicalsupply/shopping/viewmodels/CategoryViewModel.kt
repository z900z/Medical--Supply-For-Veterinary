package com.example.medicalsupply.shopping.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.utils.KeyProducts
import com.example.medicalsupply.models.Category
import com.example.medicalsupply.models.Product
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val category: Category) : ViewModel() {
    private val firestore = Firebase.firestore

    private val _offerProductSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val offerProductSF = _offerProductSF.asStateFlow()

    private val _bestProductSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProductSF = _bestProductSF.asStateFlow()

    init {
        fetchOfferProducts()
        fetchBestProducts()
    }

    private fun fetchOfferProducts() {
        viewModelScope.launch {
            _offerProductSF.emit(Resource.Loading())
        }
        firestore.collection(KeyProducts).whereEqualTo("category", category.category)
            .whereNotEqualTo("offerPercentage", null).get()
            .addOnSuccessListener {
                viewModelScope.launch {
                    val product = it.toObjects(Product::class.java)
                    _offerProductSF.emit(Resource.Success(product))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _offerProductSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    private fun fetchBestProducts() {
        viewModelScope.launch {
            _bestProductSF.emit(Resource.Loading())
        }
        firestore.collection(KeyProducts).whereEqualTo("category", category.category)
            .whereEqualTo("offerPercentage", null).get()
            .addOnSuccessListener {
                viewModelScope.launch {
                    val product = it.toObjects(Product::class.java)
                    _bestProductSF.emit(Resource.Success(product))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProductSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }


}
package com.example.medicalsupply.shopping.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.models.Product
import com.example.medicalsupply.utils.KeyProducts
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    private val _productsSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val productsSF = _productsSF.asStateFlow()
    fun fetchProducts() {
        viewModelScope.launch {
            _productsSF.emit(Resource.Loading())
        }
        firestore.collection(KeyProducts).get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val productsResult = result.toObjects(Product::class.java)
                    _productsSF.emit(Resource.Success(productsResult))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _productsSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }


}
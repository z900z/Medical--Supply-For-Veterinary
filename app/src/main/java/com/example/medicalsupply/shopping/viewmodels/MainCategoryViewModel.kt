package com.example.medicalsupply.shopping.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.firebase.FirebaseCommon
import com.example.medicalsupply.models.CartProduct
import com.example.medicalsupply.utils.KeyProducts
import com.example.medicalsupply.models.Product
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainCategoryViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _bestProductsSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProductsSF: StateFlow<Resource<List<Product>>> = _bestProductsSF




    fun fetchBestProducts() {
        viewModelScope.launch {
            _bestProductsSF.emit(Resource.Loading())
        }
        db.collection(KeyProducts).whereEqualTo("category", "pet food")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val bestProductsResult = result.toObjects(Product::class.java)
                    _bestProductsSF.emit(Resource.Success(bestProductsResult))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProductsSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }


}
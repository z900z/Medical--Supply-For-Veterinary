package com.example.medicalsupply.shopping.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.firebase.FirebaseCommon
import com.example.medicalsupply.models.CartProduct
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth
    private val firebaseCommon = FirebaseCommon()

    private val _addToCartSF = MutableStateFlow<Resource<CartProduct>>(Resource.Unspecified())
    val addToCartSF = _addToCartSF.asStateFlow()

    fun addUpdateProductInCart(cartProduct: CartProduct) {
        viewModelScope.launch {
            _addToCartSF.emit(Resource.Loading())
        }
        firestore.collection(KeyUser).document(auth.uid!!).collection("cart")
            .whereEqualTo("product.id", cartProduct.product.id).get()
            .addOnSuccessListener {
                it.documents.let { documents ->
                    if (documents.isEmpty()) {//Add new product
                        addNewProduct(cartProduct)
                    } else {
                        var product = documents.first().toObject(CartProduct::class.java)
                        cartProduct.quantity = product!!.quantity
                        if (product == cartProduct) { // Increase quantity
                            val documentId = documents.first().id
                            increaseQuantity(documentId, cartProduct)
                        }
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch { _addToCartSF.emit(Resource.Error(it.message.toString())) }
            }


    }

    private fun addNewProduct(cartProduct: CartProduct) {
        firebaseCommon.addProductToCart(cartProduct) { addedProduct, e ->
            viewModelScope.launch {
                if (e == null)
                    _addToCartSF.emit(Resource.Success(addedProduct!!))
                else
                    _addToCartSF.emit(Resource.Error(e.message.toString()))

            }
        }


    }

    private fun increaseQuantity(documentId: String, cartProduct: CartProduct) {
        firebaseCommon.increaseQuantity(documentId) { _, e ->
            viewModelScope.launch {
                if (e == null)
                    _addToCartSF.emit(Resource.Success(cartProduct))
                else
                    _addToCartSF.emit(Resource.Error(e.message.toString()))

            }
        }


    }

}
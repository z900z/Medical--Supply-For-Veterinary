package com.example.medicalsupply.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    private val _orderSF = MutableStateFlow<Resource<Order>>(Resource.Unspecified())
    val orderSF = _orderSF.asStateFlow()

    fun placeOrder(order: Order) {
        viewModelScope.launch { _orderSF.emit(Resource.Loading()) }
        firestore.runBatch {
            //TODO: Add the order into user-order collection
            //TODO: Add the order into orders collection
            //TODO: Delete the products from user-cart collection

            firestore.collection(KeyUser).document(auth.uid!!)
                .collection("orders")
                .document()
                .set(order)

            firestore.collection("orders").document().set(order)

            firestore.collection(KeyUser).document(auth.uid!!).collection("cart").get()
                .addOnSuccessListener {
                    it.documents.forEach { documents -> // DocumentSnapshot
                        documents.reference.delete()
                    }
                }
        }.addOnSuccessListener {
            viewModelScope.launch { _orderSF.emit(Resource.Success(order)) }
        }.addOnFailureListener {
            viewModelScope.launch { _orderSF.emit(Resource.Error(it.message.toString())) }
        }

    }

}
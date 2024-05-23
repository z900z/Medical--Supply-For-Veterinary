package com.example.medicalsupply.shopping.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.models.Address
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BillingViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    private val _address = MutableStateFlow<Resource<List<Address>>>(Resource.Unspecified())
    val address = _address.asStateFlow()

    init {
        getUserAddresses()
    }
    fun getUserAddresses() {
        viewModelScope.launch { _address.emit(Resource.Loading()) }
        firestore.collection(KeyUser).document(auth.uid!!).collection("address")
            .addSnapshotListener { value, error ->
                if (error!=null){
                    viewModelScope.launch { _address.emit(Resource.Error(error.message.toString()))}
                    return@addSnapshotListener
                }else{
                    val addresses = value?.toObjects(Address::class.java)
                    viewModelScope.launch {_address.emit(Resource.Success(addresses!!)) }
                }
            }
    }

}
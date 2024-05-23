package com.example.medicalsupply.shopping.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.models.Address
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    private val _addNewAddressSF = MutableStateFlow<Resource<Address>>(Resource.Unspecified())
    val addNewAddressSF = _addNewAddressSF.asStateFlow()

    private val _errorSHF= MutableSharedFlow<String>()
    val errorSHF= _errorSHF.asSharedFlow()

    fun addAddress(address: Address) {
        val validateInputs = validateInputs(address)
        if (validateInputs) {
            viewModelScope.launch { _addNewAddressSF.emit(Resource.Loading()) }
            firestore.collection(KeyUser).document(auth.uid!!).collection("address")
                .document().set(address)
                .addOnSuccessListener {
                    viewModelScope.launch { _addNewAddressSF.emit(Resource.Success(address)) }
                }.addOnFailureListener {
                    viewModelScope.launch { _addNewAddressSF.emit(Resource.Error(it.message.toString())) }
                }
        }else{
            viewModelScope.launch {
                _errorSHF.emit("All field are required")
            }
        }
    }

    private fun validateInputs(address: Address): Boolean {
        return address.addressTitle.trim().isNotEmpty() &&
                address.city.trim().isNotEmpty() &&
                address.fullName.trim().isNotEmpty() &&
                address.phone.trim().isNotEmpty() &&
                address.street.trim().isNotEmpty()
    }

    fun deleteAddress (address: Address){
        firestore.collection(KeyUser).document(auth.uid!!).collection("address")
            .whereEqualTo("phone",address.phone).get().addOnSuccessListener {
                it.documents.forEach {
                    it.reference.delete()
                }
            }
    }

}
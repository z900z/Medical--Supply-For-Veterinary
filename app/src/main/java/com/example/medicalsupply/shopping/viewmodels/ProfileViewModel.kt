package com.example.medicalsupply.shopping.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.models.ModelUser
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    private val _userSF = MutableStateFlow<Resource<ModelUser>>(Resource.Unspecified())
    val userSF = _userSF.asStateFlow()

    init {
        getUser()
    }
    fun getUser() {
        viewModelScope.launch {
            _userSF.emit(Resource.Loading())
        }

        firestore.collection(KeyUser).document(auth.uid!!)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    viewModelScope.launch {
                        _userSF.emit(Resource.Error(error.message.toString()))
                    }
                }else{
                    val user = value?.toObject(ModelUser::class.java)
                    user?.let {
                        viewModelScope.launch {
                            _userSF.emit(Resource.Success(user))
                        }
                    }
                }
            }

    }

    fun logOut(){
        auth.signOut()
    }
}
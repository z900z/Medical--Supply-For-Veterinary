package com.example.medicalsupply.shopping.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.core.App
import com.example.medicalsupply.models.ModelUser
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID

class UserAccountViewModel(
    app: Application
) : AndroidViewModel(app) {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage.reference


    private val _userSF = MutableStateFlow<Resource<ModelUser>>(Resource.Unspecified())
    val userSF = _userSF.asStateFlow()

    private val _updateInfoSF = MutableStateFlow<Resource<ModelUser>>(Resource.Unspecified())
    val updateInfoSF = _updateInfoSF.asStateFlow()


    fun getUser() {
        viewModelScope.launch {
            _userSF.emit(Resource.Loading())
        }

        firestore.collection(KeyUser).document(auth.uid!!).get()
            .addOnSuccessListener {
                val user = it.toObject(ModelUser::class.java)
                user?.let {
                    viewModelScope.launch {
                        _userSF.emit(Resource.Success(it))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _userSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    fun updateUser(user: ModelUser, imageUri: Uri?) {
        val areInputsValid = user.name.isNotEmpty() && user.email.isNotEmpty()
        if (!areInputsValid) {
            viewModelScope.launch {
                _userSF.emit(Resource.Error("Check your inputs"))
            }
            return
        }
        viewModelScope.launch {
            _updateInfoSF.emit(Resource.Loading())
        }

        if (imageUri == null) {
            saveUserInformation(user, true)
        } else {
            saveUserInformationWithNewImage(user, imageUri)
        }

    }

    private fun saveUserInformationWithNewImage(user: ModelUser, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val imageBitmap = MediaStore.Images.Media.getBitmap(
                    getApplication<App>().contentResolver,
                    imageUri
                )
                val byteArrayOutputStream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 96, byteArrayOutputStream)
                val imageByteArray = byteArrayOutputStream.toByteArray()
                val imageDirectory = storage.child("profileImages/${auth.uid}/${UUID.randomUUID()}")
                val result = imageDirectory.putBytes(imageByteArray).await()
                val imageUrl = result.storage.downloadUrl.await().toString()
                saveUserInformation(user.copy(imagePath = imageUrl),false)
            } catch (e: Exception) {
                viewModelScope.launch {
                    _updateInfoSF.emit(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    private fun saveUserInformation(user: ModelUser, shouldRetrievedOldImage: Boolean) {
        firestore.runTransaction { transaction ->
            val documentRef = firestore.collection(KeyUser).document(auth.uid!!)
            if (shouldRetrievedOldImage) {
                val currentUser = transaction.get(documentRef).toObject(ModelUser::class.java)
                val newUser = user.copy(imagePath = currentUser?.imagePath ?: "")
                transaction.set(documentRef, newUser)
            } else {
                transaction.set(documentRef, user)
            }
        }.addOnSuccessListener {
            viewModelScope.launch {
                _updateInfoSF.emit(Resource.Success(user))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _updateInfoSF.emit(Resource.Error(it.message.toString()))
            }
        }

    }


}
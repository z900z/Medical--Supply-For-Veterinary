package com.example.medicalsupply.models

data class ModelUser(
    var email: String,
    var name: String,
    var lastName: String = "",
    var userId: String = "",
    var imagePath: String = ""
) {
    constructor() : this("", "", "", "", "")
}

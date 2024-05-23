package com.example.medicalsupply.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.ActivityAuthBinding
import com.example.medicalsupply.databinding.ActivitySignUpBinding

class AuthActivity : AppCompatActivity() {
    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
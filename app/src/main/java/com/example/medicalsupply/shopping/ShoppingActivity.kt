package com.example.medicalsupply.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    private var _binding:ActivityShoppingBinding?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.shopping_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
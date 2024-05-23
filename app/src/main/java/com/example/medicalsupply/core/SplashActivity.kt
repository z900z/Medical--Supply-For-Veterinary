package com.example.medicalsupply.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.medicalsupply.auth.AuthActivity
import com.example.medicalsupply.databinding.ActivitySplashBinding
import com.example.medicalsupply.shopping.ShoppingActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)




        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.uid == null) {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, ShoppingActivity::class.java))
                finish()
            }
        }, 1500)

    }


    override fun onDestroy() {
        super.onDestroy()
        binding.progressOne.visibility = View.GONE
        binding.progressTwo.visibility = View.GONE
        _binding = null
    }
}
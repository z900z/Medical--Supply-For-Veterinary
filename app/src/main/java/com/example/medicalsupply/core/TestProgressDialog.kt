package com.example.medicalsupply.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.medicalsupply.R
import com.example.medicalsupply.ui.LoadingDialog

/** Here we implement progress Dialog*/
class TestProgressDialog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        /** 1. create loading dialog variable  */
        val loading = LoadingDialog(this)

        /** 2. start dialog  */
        loading.startLoading()

        Handler().postDelayed(object : Runnable {
            override fun run() {

                /** 3. end dialog  */
                loading.isDismiss()
            }

        }, 15000)
    }
}
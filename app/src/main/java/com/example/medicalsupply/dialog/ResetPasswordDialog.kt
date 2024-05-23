package com.example.medicalsupply.dialog

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.ResetPasswordDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext())
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    /** action of buttons*/
    val binding = ResetPasswordDialogBinding.bind(view)

    binding.btnSend.setOnClickListener {
        val email = binding.etResetPassword.text.toString().trim()
        if (email.isEmpty()){
            Toast.makeText(requireContext(),"please enter your email", Toast.LENGTH_LONG).show()
        }else{
            onSendClick(email)
            binding.etResetPassword.text=null
        }

    }

    binding.btnCancel.setOnClickListener {
        dialog.dismiss()
    }


}
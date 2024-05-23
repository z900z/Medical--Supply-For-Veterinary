package com.example.medicalsupply.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.FragmentLoginBinding
import com.example.medicalsupply.dialog.setupBottomSheetDialog
import com.example.medicalsupply.shopping.ShoppingActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
//  code .........................................................................................
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)



        binding.btnGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPass.text.toString().trim()
            validate(email, password)
        })

        binding.tvForgetPassword.setOnClickListener {
            setupBottomSheetDialog {email->
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(),"Reset link was sent to your email",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(requireContext(),"Error ${it.localizedMessage}",Toast.LENGTH_LONG).show()
                    }
            }
        }

    }

    private fun validate(email: String, password: String) {
        when {
            email.isEmpty() -> binding.etEmail.error = "name is required"
            password.isEmpty() -> binding.etPass.error = "password is required"
            password.length < 6 -> binding.etPass.error = "password should not be less than 6"
            else -> login(email, password)
        }
    }


    private fun login(email: String, password: String) {
        binding.progress.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE

                startActivity(Intent(requireContext(), ShoppingActivity::class.java))
                requireActivity().finish()

                binding.etEmail.text = null
                binding.etPass.text = null

            }.addOnFailureListener {
                binding.progress.visibility = View.GONE
                Toast.makeText(
                    requireActivity(), "failed: " + it.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
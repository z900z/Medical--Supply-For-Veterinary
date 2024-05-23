package com.example.medicalsupply.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.utils.KeyUser
import com.example.medicalsupply.R
import com.example.medicalsupply.auth.password_handling.PasswordStrengthCalculator
import com.example.medicalsupply.auth.password_handling.StrengthLevel
import com.example.medicalsupply.databinding.FragmentSignUpBinding
import com.example.medicalsupply.models.ModelUser
import com.example.medicalsupply.shopping.ShoppingActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private var color: Int = R.color.weak
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }
//code..............................................................................................
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)

        val passwordStrengthCalculator = PasswordStrengthCalculator()
        binding.etPassword.addTextChangedListener(passwordStrengthCalculator)
        // Observers
        passwordStrengthCalculator.strengthLevel.observe(viewLifecycleOwner,
            Observer { strengthLevel ->
                displayStrengthLevel(strengthLevel)
            })
        passwordStrengthCalculator.strengthColor.observe(viewLifecycleOwner,
            Observer { strengthColor ->
                color = strengthColor
            })

        passwordStrengthCalculator.lowerCase.observe(viewLifecycleOwner, Observer { value ->
            displayPasswordSuggestions(value, binding.lowerCaseImg, binding.lowerCaseTxt)
        })
        passwordStrengthCalculator.upperCase.observe(viewLifecycleOwner, Observer { value ->
            displayPasswordSuggestions(value, binding.upperCaseImg, binding.upperCaseTxt)
        })
        passwordStrengthCalculator.digit.observe(viewLifecycleOwner, Observer { value ->
            displayPasswordSuggestions(value, binding.digitImg, binding.digitTxt)
        })
        passwordStrengthCalculator.specialChar.observe(viewLifecycleOwner, Observer { value ->
            displayPasswordSuggestions(value, binding.specialCharImg, binding.specialCharTxt)

        })


    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {

        binding.btnSignUp.setOnClickListener {
            if (strengthLevel == StrengthLevel.STRONG) {
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                validate(email, password, name)
            } else {
                Toast.makeText(
                    requireContext(),
                    "the password should be strong\nand at least 7 character",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.strengthLevelTxt.text = strengthLevel.name
        binding.strengthLevelTxt.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.strengthLevelIndicator.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )

    }

    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if (value == 1) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.very_strong))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.very_strong))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }
    }

    private fun validate(email: String, password: String, name: String) {
        if (email.isEmpty()) {
            binding.etEmail.error = "email required"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "password required"
        } else if (password.length < 7) {
            binding.etPassword.error = "password should not be less than 7"
        } else if (name.isEmpty()) {
            binding.etName.error = "name required"
        } else {
            register(name, email, password)
        }
    }

    private fun register(name: String, email: String, password: String) {
        binding.progress.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let { user ->
                    sendDataToRealTime(name, email, user.uid)
                }

            }.addOnFailureListener {
                binding.progress.visibility = View.GONE
                Toast.makeText(
                    requireContext(), "failed: ${it.localizedMessage}", Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun sendDataToRealTime(name: String, email: String, userId: String) {
        db.collection(KeyUser)
            .document(userId)
            .set(ModelUser(email,name,"",userId))
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE

                startActivity(Intent(requireContext(), ShoppingActivity::class.java))
                requireActivity().finish()

            }.addOnFailureListener {
                binding.progress.visibility = View.GONE

                Toast.makeText(
                    requireContext(),
                    "sent data to realtime Failed: " + it.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
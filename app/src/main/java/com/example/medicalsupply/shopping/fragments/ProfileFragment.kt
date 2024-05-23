package com.example.medicalsupply.shopping.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.medicalsupply.R
import com.example.medicalsupply.auth.AuthActivity
import com.example.medicalsupply.databinding.FragmentProfileBinding
import com.example.medicalsupply.products_adder.ProductsAdder
import com.example.medicalsupply.shopping.viewmodels.BillingViewModel
import com.example.medicalsupply.shopping.viewmodels.ProfileViewModel
import com.example.medicalsupply.utils.Resource
import com.example.medicalsupply.utils.showBottomNavigationView
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUserAccountFragment())
        }

        binding.linearAllOrders.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAllOrdersFragment())
        }

        binding.linearBilling.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToBillingFragment(
                    0f,
                    emptyArray(),
                    false
                )
            )
        }

        binding.linearAddProduct.setOnClickListener {
            startActivity(Intent(requireContext(),ProductsAdder::class.java))
        }
        /*
        binding.linearTrackOrder.setOnClickListener {

        }  */

        binding.linearLogOut.setOnClickListener {
            viewModel.logOut()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }

        binding.tvVersion.text = "Version ${BuildConfig.VERSION_NAME}"

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.userSF.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                           binding.progressbarSettings.visibility =View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.progressbarSettings.visibility =View.GONE
                            Glide.with(requireView()).load(it.data!!.imagePath).error(ColorDrawable(
                                Color.BLACK)).into(binding.imageUser)
                            binding.tvUserName.text ="${it.data.name} ${it.data.lastName}"
                        }

                        is Resource.Error -> {
                            binding.progressbarSettings.visibility =View.GONE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }

}
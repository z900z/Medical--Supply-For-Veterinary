package com.example.medicalsupply.shopping.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.medicalsupply.databinding.FragmentAddressBinding
import com.example.medicalsupply.models.Address
import com.example.medicalsupply.shopping.viewmodels.AddressViewModel
import com.example.medicalsupply.utils.Resource
import kotlinx.coroutines.flow.collectLatest

class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    val viewModel by viewModels<AddressViewModel>()
    val args by navArgs<AddressFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.addNewAddressSF.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressbarAddress.visibility=View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressbarAddress.visibility=View.INVISIBLE
                        findNavController().navigateUp()
                    }

                    is Resource.Error -> {
                        binding.progressbarAddress.visibility=View.INVISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorSHF.collectLatest {
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = args.address
        if (address==null){
            binding.buttonDelelte.visibility = View.GONE
        }else{
            binding.apply {
                edAddressTitle.setText(address.addressTitle)
                edFullName.setText(address.fullName)
                edStreet.setText(address.street)
                edPhone.setText(address.phone)
                edCity.setText(address.city)
                edState.setText(address.state)
            }
        }


        binding.apply {
            buttonSave.setOnClickListener {
                val addressTitle = edAddressTitle.text.toString().trim()
                val city = edCity.text.toString().trim()
                val fullName = edFullName.text.toString().trim()
                val phone = edPhone.text.toString().trim()
                val street = edStreet.text.toString().trim()
                val state = edState.text.toString().trim()
                val address = Address(addressTitle, fullName, street, phone, city, state)

                viewModel.addAddress(address)
            }
        }

        binding.buttonDelelte.setOnClickListener {
            address.let {
              viewModel.deleteAddress(address!!)
                Toast.makeText(requireContext(), "The address was deleted", Toast.LENGTH_SHORT).show()

                binding.apply {
                    edAddressTitle.setText("")
                    edFullName.setText("")
                    edStreet.setText("")
                    edPhone.setText("")
                    edCity.setText("")
                    edState.setText("")
                }
            }

        }

        binding.imageAddressClose.setOnClickListener {
            findNavController().navigateUp()
        }


    }

}
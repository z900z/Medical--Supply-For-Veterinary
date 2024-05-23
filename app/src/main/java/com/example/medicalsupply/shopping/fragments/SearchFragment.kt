package com.example.medicalsupply.shopping.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.databinding.FragmentSearchBinding
import com.example.medicalsupply.models.Product
import com.example.medicalsupply.shopping.adapters.BestProductAdapter
import com.example.medicalsupply.shopping.adapters.SearchAdapter
import com.example.medicalsupply.shopping.viewmodels.SearchViewModel
import com.example.medicalsupply.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }
    private var productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter.onClick = {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(it)
            )
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsSF.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.searchProgress.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.searchProgress.visibility = View.GONE
                            productList.addAll(it.data as ArrayList<Product>)

                            searchAdapter.productList = productList
                            binding.searchRv.adapter = searchAdapter
                        }

                        is Resource.Error -> {
                            binding.searchProgress.visibility = View.GONE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }

                        else -> Unit
                    }
                }
            }
        }



        binding.search.doAfterTextChanged {
            var query = it.toString()

            val newList = productList.filter {
                it.name.toLowerCase().contains(query.toLowerCase())
            }

            if (!newList.isNullOrEmpty())
                searchAdapter.updateAdapter(newList as ArrayList<Product>)

        }


    }


}
package com.example.medicalsupply.shopping.categories

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.FragmentBaseCategoryBinding
import com.example.medicalsupply.databinding.ItemProductRvBinding
import com.example.medicalsupply.shopping.adapters.BestProductAdapter
import com.example.medicalsupply.shopping.fragments.HomeFragmentDirections
import com.example.medicalsupply.shopping.viewmodels.MainCategoryViewModel
import com.example.medicalsupply.utils.showBottomNavigationView

open class BaseCategoryFragment : Fragment(R.layout.fragment_base_category) {
    protected lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductAdapter by lazy { BestProductAdapter() }
    protected val bestProductAdapter: BestProductAdapter by lazy { BestProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOfferRv()
        setUpBestProductRv()

        bestProductAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
            )
        }

        offerAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
            )
        }

    }

    private fun setUpBestProductRv() {
        binding.rvBestProductsCategory.adapter = bestProductAdapter
    }

    private fun setUpOfferRv() {
        binding.rvOfferCategory.adapter = offerAdapter
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }

}
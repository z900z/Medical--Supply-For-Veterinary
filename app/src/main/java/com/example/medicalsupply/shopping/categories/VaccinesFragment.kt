package com.example.medicalsupply.shopping.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.medicalsupply.models.Category
import com.example.medicalsupply.shopping.viewmodels.CategoryViewModel
import com.example.medicalsupply.shopping.viewmodels.factory.BaseCategoryViewModelFactory
import com.example.medicalsupply.utils.Resource
import kotlinx.coroutines.flow.collectLatest

/** for medical students*/

class VaccinesFragment : BaseCategoryFragment() {
    private val TAG = "MedicalFragment"
    private val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactory(Category.Vaccines)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()


        lifecycleScope.launchWhenStarted {
            viewModel.bestProductSF.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        bestProductAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.offerProductSF.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        offerAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun showLoading() {
        binding.progressProducts.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressProducts.visibility = View.GONE
    }
}
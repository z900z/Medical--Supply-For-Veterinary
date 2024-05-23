package com.example.medicalsupply.shopping.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicalsupply.models.Category
import com.example.medicalsupply.shopping.viewmodels.CategoryViewModel

class BaseCategoryViewModelFactory(
    private val category: Category
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(category) as T
    }
}
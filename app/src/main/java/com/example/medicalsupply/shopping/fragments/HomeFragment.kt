package com.example.medicalsupply.shopping.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.FragmentHomeBinding
import com.example.medicalsupply.shopping.adapters.HomeViewPagerAdapter
import com.example.medicalsupply.shopping.categories.HormonesFragment
import com.example.medicalsupply.shopping.categories.PetFoodFragment
import com.example.medicalsupply.shopping.categories.VaccinesFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val categoriesFragments = arrayListOf<Fragment>(
            PetFoodFragment(),
            VaccinesFragment(),
            HormonesFragment(),
        )

        binding.viewPagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewPagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPager2Adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Pet Food"
                1 -> tab.text = "Vaccines"
                2 -> tab.text = "Hormones"
            }

        }.attach()


      /*  binding.searchBar.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.storeapp.presentation.menu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.databinding.FragmentProductsBinding
import com.example.storeapp.presentation.menu.profile.adapter.ProductsAdapter

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private var adapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViewModel()
        getDataFromViewModel()
    }

    private fun setupAdapter() {
        binding.recyclerViewFrgProducts.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
    }

    private fun getDataFromViewModel() {
        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            adapter.setProductList(productList)
        }
    }
}
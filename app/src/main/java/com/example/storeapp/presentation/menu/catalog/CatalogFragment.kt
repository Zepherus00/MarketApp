package com.example.storeapp.presentation.menu.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storeapp.R
import com.example.storeapp.data.ProductModel
import com.example.storeapp.data.Repository
import com.example.storeapp.databinding.FragmentCatalogBinding
import com.example.storeapp.presentation.menu.catalog.adapter.CarouselAdapter
import com.example.storeapp.presentation.menu.catalog.adapter.CatalogAdapter

class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding
    private val repository = Repository()
    private var productsList = emptyList<ProductModel>()
    private var sortedList = emptyList<ProductModel>()
    private var favoritesProductList = mutableListOf<String>()
    private var carouselList = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски")
    private var adapterCatalog = CatalogAdapter(
        onFavoriteBtnClicked = { itemId ->
            onItemFavoriteBtnClicked(itemId)
        },
        onProductClick = { itemId ->
            onProductClickListener(itemId)
        }
    )
    private var adapterCarousel = CarouselAdapter(
        onItemClicked = { position -> onItemCarouselClicked(position) },
        onBtnCancelClicked = { onBtnCancelCarouselClicked() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initData()
        setListeners()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProductsFrgCatalog.adapter = adapterCatalog
        binding.recyclerViewCarouselFrgCatalog.adapter = adapterCarousel
    }

    private fun initData() {
        adapterCarousel.selectItem("Смотреть все")
        repository.getData { products ->
            productsList = products
            sortedList = products
            adapterCatalog.setProductList(productsList)
        }
        val id = arguments?.getString("key_string") ?: ""
        if (id.isNotBlank()) {
            favoritesProductList.add(id)
            adapterCatalog.setFavoriteList(favoritesProductList)
        }
    }

    private fun setListeners() {
        with(binding) {
            btnSortFrgCatalog.setOnClickListener {
                if (layoutSortingFrgCatalog.isVisible) {
                    layoutSortingFrgCatalog.visibility = View.GONE
                } else {
                    layoutSortingFrgCatalog.visibility = View.VISIBLE
                }
            }

            btnSortPopularFrgCatalog.setOnClickListener {
                btnSortFrgCatalog.text = "По популярности"
                layoutSortingFrgCatalog.visibility = View.GONE

                sortedList = sortedList.sortedByDescending { it.feedback?.rating }
                sortedList.forEach {
                    Log.d("error", it.price.priceWithDiscount)
                }
                adapterCatalog.setProductList(sortedList)
            }

            btnPriceDownFrgCatalog.setOnClickListener {
                btnSortFrgCatalog.text = "По уменьшению цены"
                layoutSortingFrgCatalog.visibility = View.GONE

                sortedList = sortedList.sortedByDescending { it.price.priceWithDiscount.toInt() }
                adapterCatalog.setProductList(sortedList)
            }

            btnPriceUpFrgCatalog.setOnClickListener {
                btnSortFrgCatalog.text = "По возрастанию цены"
                layoutSortingFrgCatalog.visibility = View.GONE

                sortedList = sortedList.sortedBy { it.price.priceWithDiscount.toInt() }
                adapterCatalog.setProductList(sortedList)
            }
        }
    }

    private fun onItemCarouselClicked(position: Int) {
        adapterCarousel.selectItem(carouselList[position])
        val filterList: List<ProductModel>

        when (carouselList[position]) {
            "Смотреть все" -> {
                filterList = productsList
                adapterCatalog.setProductList(filterList)
            }

            "Лицо" -> {
                filterList = sortedList.filter { it.tags.contains("face") }
                adapterCatalog.setProductList(filterList)
            }

            "Тело" -> {
                filterList = sortedList.filter { it.tags.contains("body") }
                adapterCatalog.setProductList(filterList)
            }

            "Загар" -> {
                filterList = sortedList.filter { it.tags.contains("suntan") }
                adapterCatalog.setProductList(filterList)
            }

            else -> {
                filterList = sortedList.filter { it.tags.contains("mask") }
                adapterCatalog.setProductList(filterList)
            }
        }
    }

    private fun onBtnCancelCarouselClicked() {
        adapterCatalog.setProductList(sortedList)
    }

    private fun onItemFavoriteBtnClicked(itemId: String) {
        favoritesProductList.add(itemId)
        productsList.find { it.id == itemId }?.let { item ->
            repository.insertProduct(item)
        }
        adapterCatalog.setFavoriteList(favoritesProductList)
    }

    private fun onProductClickListener(itemId: String) {
        val item = productsList.find { it.id == itemId }
        val isFavorite = favoritesProductList.contains(itemId)
        item?.let {
            val bundle = Bundle()
            bundle.putParcelable("key_item", item)
            bundle.putBoolean("key_boolean", isFavorite)
            findNavController().navigate(R.id.action_catalogFragment_to_itemFragment, bundle)
        }
    }
}
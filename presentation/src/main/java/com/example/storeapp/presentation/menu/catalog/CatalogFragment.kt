package com.example.storeapp.presentation.menu.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.model.SaveProductModel
import com.example.storeapp.R
import com.example.storeapp.databinding.FragmentCatalogBinding
import com.example.storeapp.presentation.menu.catalog.adapter.CarouselAdapter
import com.example.storeapp.presentation.menu.catalog.adapter.CatalogAdapter
import com.example.storeapp.presentation.menu.catalog.state.CatalogState
import com.example.storeapp.presentation.utilities.makeGone
import com.example.storeapp.presentation.utilities.makeVisible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding
    private val viewModel by viewModel<CatalogViewModel>()
    private var productsList = emptyList<SaveProductModel>()
    private var sortedList = emptyList<SaveProductModel>()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        CatalogState.Success -> {
                            binding.progressCatalog.makeGone()
                            binding.btnRefreshFrgCatalog.makeGone()
                        }

                        CatalogState.Loading -> {
                            binding.progressCatalog.makeVisible()
                            binding.btnRefreshFrgCatalog.makeGone()
                        }

                        is CatalogState.Error -> {
                            binding.progressCatalog.makeGone()
                            binding.btnRefreshFrgCatalog.makeVisible()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProductsFrgCatalog.adapter = adapterCatalog
        binding.recyclerViewCarouselFrgCatalog.adapter = adapterCarousel
    }

    private fun initData() {
        Log.d("MyLog", "gfdfg")

        adapterCarousel.selectItem("Смотреть все")
        viewModel.productsFromNetwork.observe(viewLifecycleOwner) { products ->
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

            btnRefreshFrgCatalog.setOnClickListener {
                viewModel.onLoadDataClick()
            }
        }
    }

    private fun onItemCarouselClicked(position: Int) {
        adapterCarousel.selectItem(carouselList[position])
        val filterList = when (carouselList[position]) {
            "Смотреть все" -> productsList
            "Лицо" -> filterListByTag("face")
            "Тело" -> filterListByTag("body")
            "Загар" -> filterListByTag("suntan")
            else -> filterListByTag("mask")
        }
        adapterCatalog.setProductList(filterList)
    }

    private fun filterListByTag(tag: String): List<SaveProductModel> {
        return sortedList.filter { it.tags.contains(tag) }
    }

    private fun onBtnCancelCarouselClicked() {
        adapterCatalog.setProductList(sortedList)
    }

    private fun onItemFavoriteBtnClicked(itemId: String) {
        favoritesProductList.add(itemId)
        productsList.find { it.id == itemId }?.let { param ->
            viewModel.markProductAsFavorite(param)
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
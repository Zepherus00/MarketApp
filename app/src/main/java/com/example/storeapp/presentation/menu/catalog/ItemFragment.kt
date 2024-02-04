package com.example.storeapp.presentation.menu.catalog

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storeapp.R
import com.example.storeapp.data.ProductModel
import com.example.storeapp.data.Repository
import com.example.storeapp.databinding.FragmentItemBinding
import com.example.storeapp.presentation.menu.catalog.adapter.ImageAdapter
import com.example.storeapp.utilities.getImageList
import com.example.storeapp.utilities.parcelable
import com.example.storeapp.utilities.selectFormCountProducts
import com.example.storeapp.utilities.selectFormCountReview

class ItemFragment : Fragment() {
    private lateinit var binding: FragmentItemBinding
    private val repository = Repository()
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()?.let { item ->
            setupAdapter(item)
            setData(item)
            setListeners(item.id)
        }
    }

    private fun getData(): ProductModel? {
        isFavorite = arguments?.getBoolean("key_boolean") ?: false
        return arguments?.parcelable<ProductModel>("key_item")
    }

    private fun setupAdapter(item: ProductModel) {
        with(binding) {
            val imageList = getImageList(item.id)
            val adapter = ImageAdapter(imageList)
            viewPagerFrgItem.adapter = adapter
            springDotsFrgItem.attachTo(viewPagerFrgItem)
        }
    }

    private fun setData(item: ProductModel) {
        with(binding) {
            if (isFavorite)
                imgFavoriteFrgItem.setImageResource(R.drawable.ic_favorite)
            else {
                imgFavoriteFrgItem.setImageResource(R.drawable.ic_favorite_empty)
                setFavoriteBtnListener(item)
            }

            titleFrgItem.text = item.title
            subtitleFrgItem.text = item.subtitle
            availableFrgItem.text = resources.getString(
                R.string.count_available_products,
                item.available.toString(),
                selectFormCountProducts(item.available)
            )

            if (item.feedback != null) {
                ratingBarFrgItem.rating = item.feedback.rating.toFloat()
                ratingTextFrgItem.text = item.feedback.rating.toString()
                reviewCountFrgItem.text = resources.getString(
                    R.string.count_item,
                    item.feedback.count.toString(),
                    selectFormCountReview(item.feedback.count)
                )
            } else {
                ratingBarFrgItem.visibility = View.GONE
                ratingTextFrgItem.visibility = View.GONE
                reviewCountFrgItem.visibility = View.GONE
            }

            currentPriceFrgItem.text = getString(
                R.string.current_price_frg_item,
                item.price.priceWithDiscount,
                item.price.unit
            )
            oldPriceFrgItem.text =
                getString(R.string.old_price_frg_item, item.price.price, item.price.unit)
            oldPriceFrgItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            discountFrgItem.text = "-${item.price.discount}%"
            brandTitle.text = item.title
            subscriptionTextFrgItem.text = item.description
            vendorCodeTitleFrgItem.text = item.info[0].title
            vendorCodeTextFrgItem.text = item.info[0].value
            usedAreaTitleFrgItem.text = item.info[1].title
            usedAreaTextFrgItem.text = item.info[1].value
            countryTitleFrgItem.text = item.info[2].title
            countryTextFrgItem.text = item.info[2].value
            ingredientsTextFrgItem.text = item.ingredients
            currentPriceCartFrgItem.text =
                "${item.price.priceWithDiscount} ${item.price.unit}"
            oldPriceCartFrgItem.text = "${item.price.price} ${item.price.unit}"
            oldPriceCartFrgItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    private fun setFavoriteBtnListener(item: ProductModel) {
        with(binding) {
            imgFavoriteFrgItem.setOnClickListener {
                isFavorite = true
                repository.insertProduct(item)
                imgFavoriteFrgItem.isClickable = false
                imgFavoriteFrgItem.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    private fun setListeners(id: String) {
        binding.btnBackFrgItem.setOnClickListener {
            val bundle = Bundle()
            if (isFavorite) bundle.putString("key_string", id)
            findNavController().navigate(R.id.action_itemFragment_to_catalogFragment, bundle)
        }

        binding.btnSubscriptionBrand.setOnClickListener {
            if (binding.btnSubscriptionBrand.text == "Скрыть") {
                binding.subscriptionTitleFrgItem.visibility = View.GONE
                binding.subscriptionTextFrgItem.visibility = View.GONE
                binding.btnBrandFrgItem.visibility = View.GONE
                binding.btnSubscriptionBrand.text = "Подробнее"
            } else {
                binding.subscriptionTitleFrgItem.visibility = View.VISIBLE
                binding.subscriptionTextFrgItem.visibility = View.VISIBLE
                binding.btnBrandFrgItem.visibility = View.VISIBLE
                binding.btnSubscriptionBrand.text = "Скрыть"
            }
        }

        binding.btnIngredientsFrgItem.setOnClickListener {
            if (binding.btnIngredientsFrgItem.text == "Подробнее") {
                binding.ingredientsTextFrgItem.maxLines = 100
                binding.btnIngredientsFrgItem.text = "Скрыть"
            } else {
                binding.ingredientsTextFrgItem.maxLines = 2
                binding.btnIngredientsFrgItem.text = "Подробнее"
            }
        }
    }
}
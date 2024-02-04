package com.example.storeapp.presentation.menu.catalog.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.storeapp.R
import com.example.storeapp.data.ProductModel
import com.example.storeapp.databinding.RvItemBinding
import com.example.storeapp.utilities.getImageList
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class CatalogAdapter(
    private val onFavoriteBtnClicked: (String) -> Unit,
    private val onProductClick: (String) -> Unit
) : RecyclerView.Adapter<CatalogViewHolder>() {
    private var itemsList = emptyList<ProductModel>()
    private var favoritesProductList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val item = itemsList[position]
        setupAdapter(holder, item)
        setData(holder, item)
        setListeners(holder, item)
    }

    private fun setupAdapter(holder: CatalogViewHolder, item: ProductModel) {
        val imageList = getImageList(item.id)
        val adapter = ImageAdapter(imageList)
        holder.vwPager.adapter = adapter
        holder.dotsIndicator.attachTo(holder.vwPager)
    }

    private fun setData(holder: CatalogViewHolder, item: ProductModel) {
        holder.oldPrice.text = "${item.price.priceWithDiscount} ${item.price.unit}"
        holder.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        holder.price.text = "${item.price.price} ${item.price.unit}"
        holder.discount.text = "-${item.price.discount}%"
        holder.title.text = item.title
        holder.description.text = item.subtitle
        if (item.feedback != null) {
            holder.rating.text = item.feedback.rating.toString()
            holder.countRating.text = "(${item.feedback.count})"
        } else {
            holder.rating.visibility = View.GONE
            holder.countRating.visibility = View.GONE
        }

        if (item.id in favoritesProductList)
            holder.btnFavourite.setImageResource(R.drawable.ic_favorite)
        else
            holder.btnFavourite.setImageResource(
                R.drawable.ic_favorite_empty
            )
    }

    private fun setListeners(holder: CatalogViewHolder, item: ProductModel) {
        holder.btnFavourite.setOnClickListener {
            holder.btnFavourite.setImageResource(R.drawable.ic_favorite)
            onFavoriteBtnClicked(item.id)
        }

        holder.itemView.setOnClickListener {
            onProductClick(item.id)
        }
    }

    fun setFavoriteList(favoritesList: MutableList<String>) {
        favoritesProductList = favoritesList
        notifyDataSetChanged()
    }

    fun setProductList(list: List<ProductModel>) {
        itemsList = list
        notifyDataSetChanged()
    }
}

class CatalogViewHolder(binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val vwPager: ViewPager2 = binding.viewPagerRvItem
    val dotsIndicator: WormDotsIndicator = binding.springDotsRvItem
    val btnFavourite: AppCompatImageView = binding.btnFavoriteRvItem
    val oldPrice: MaterialTextView = binding.oldPriceRvItem
    val price: MaterialTextView = binding.priceRvItem
    val discount: MaterialTextView = binding.discountRvItem
    val title: MaterialTextView = binding.titleRvItem
    val description: MaterialTextView = binding.descriptionRvItem
    val rating: MaterialTextView = binding.rateRvItem
    val countRating: MaterialTextView = binding.countRatingRvItem
}


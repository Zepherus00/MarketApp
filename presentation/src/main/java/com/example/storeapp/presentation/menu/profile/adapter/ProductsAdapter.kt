package com.example.storeapp.presentation.menu.profile.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.storeapp.R
import com.example.data.room.product.Product
import com.example.storeapp.databinding.RvItemBinding
import com.example.storeapp.presentation.menu.catalog.adapter.ImageAdapter
import com.example.storeapp.presentation.utilities.getImageList
import com.google.android.material.textview.MaterialTextView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder>() {
    private var productList = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = productList[position]
        setupAdapterAndDotsIndicator(item, holder)
        setupData(item, holder)
    }

    private fun setupAdapterAndDotsIndicator(item: Product, holder: ProductsViewHolder) {
        val imageList = getImageList(item.id)
        val adapter = ImageAdapter(imageList)
        holder.vwPager.adapter = adapter
        holder.dotsIndicator.attachTo(holder.vwPager)
    }

    private fun setupData(item: Product, holder: ProductsViewHolder) {
        holder.oldPrice.text = "${item.priceWithDiscount} ${item.unit}"
        holder.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.price.text = "${item.price} ${item.unit}"
        holder.discount.text = "-${item.discount}%"
        holder.title.text = item.title
        holder.description.text = item.subtitle
        holder.rating.text = item.feedbackRating.toString()
        holder.countRating.text = "(${item.feedbackCount})"
        holder.btnFavourite.setImageResource(R.drawable.ic_favorite)
    }

    fun setProductList(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }
}

class ProductsViewHolder(binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
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


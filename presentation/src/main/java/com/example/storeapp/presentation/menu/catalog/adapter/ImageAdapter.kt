package com.example.storeapp.presentation.menu.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.databinding.ImageLayoutBinding

class ImageAdapter(private val imageUrls: List<Int>) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageUrls[position])
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}

class ImageViewHolder(binding: ImageLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    val imageView: ImageView = binding.image
}
package com.example.storeapp.presentation.menu.catalog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.databinding.RvCarouselItemBinding
import com.google.android.material.textview.MaterialTextView

class CarouselAdapter(
    private val onItemClicked: (Int) -> Unit,
    private val onBtnCancelClicked: () -> Unit
) : RecyclerView.Adapter<CarouselViewHolder>() {
    private var itemsList = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски")
    private var selectedItem = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            RvCarouselItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        setupData(holder, position)
        setupListeners(holder, position)
    }

    private fun setupData(holder: CarouselViewHolder, position: Int) {
        val item = itemsList[position]

        holder.textItem.text = item
        if (item == selectedItem) {
            holder.btnCancel.visibility = View.VISIBLE
            holder.item.setBackgroundResource(R.drawable.bg_dark_blue_r100)
            holder.textItem.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.element_white
                )
            )
        } else {
            holder.btnCancel.visibility = View.GONE
            holder.item.setBackgroundResource(R.drawable.bg_light_grey_r100)
            holder.textItem.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.txt_grey
                )
            )
        }
    }

    private fun setupListeners(holder: CarouselViewHolder, position: Int) {
        holder.item.setOnClickListener {
            onItemClicked(position)
        }

        holder.btnCancel.setOnClickListener {
            cancelItem()
            onBtnCancelClicked()
        }
    }

    private fun cancelItem() {
        selectedItem = ""
        notifyDataSetChanged()
    }

    fun selectItem(item: String) {
        selectedItem = item
        notifyDataSetChanged()
    }
}

class CarouselViewHolder(binding: RvCarouselItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val item: LinearLayoutCompat = binding.layoutRvCarouselItem
    val textItem: MaterialTextView = binding.textRvCarouselItem
    val btnCancel: AppCompatImageView = binding.btnCancelRvCarouselItem
}


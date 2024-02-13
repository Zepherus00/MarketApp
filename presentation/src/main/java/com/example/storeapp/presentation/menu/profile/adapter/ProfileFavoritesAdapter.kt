package com.example.storeapp.presentation.menu.profile.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.storeapp.presentation.menu.profile.BrandsFragment
import com.example.storeapp.presentation.menu.profile.FavoritesFragment
import com.example.storeapp.presentation.menu.profile.ProductsFragment

class ProfileFavoritesAdapter(fragment: FavoritesFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductsFragment()
            else -> BrandsFragment()
        }
    }
}
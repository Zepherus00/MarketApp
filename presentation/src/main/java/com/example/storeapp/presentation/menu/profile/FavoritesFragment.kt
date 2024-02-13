package com.example.storeapp.presentation.menu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.storeapp.R
import com.example.storeapp.databinding.FragmentFavoritesBinding
import com.example.storeapp.presentation.menu.profile.adapter.ProfileFavoritesAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: ProfileFavoritesAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setupTabLayout()
        setListener()
    }

    private fun initData() {
        viewPager = binding.viewPagerFrgFavorites
        adapter = ProfileFavoritesAdapter(this)
        viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        tabLayout = binding.tabLayoutFrgFavorites
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        tabLayout.getTabAt(0)?.setText(getString(R.string.products_text_frg_favorites))
        tabLayout.getTabAt(1)?.setText(getString(R.string.brands_text_frg_favorites))
        viewPager.isUserInputEnabled = false
    }

    private fun setListener() {
        binding.btnBackFrgFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_profileFragment)
        }
    }
}
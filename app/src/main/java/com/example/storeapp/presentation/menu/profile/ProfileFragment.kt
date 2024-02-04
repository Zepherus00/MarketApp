package com.example.storeapp.presentation.menu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.storeapp.R
import com.example.storeapp.databinding.FragmentProfileBinding
import com.example.storeapp.entity.product.Product
import com.example.storeapp.utilities.productDao
import com.example.storeapp.utilities.restartActivity
import com.example.storeapp.utilities.selectFormCountFavoriteProducts
import com.example.storeapp.utilities.userDao
import com.example.storeapp.utilities.userModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        getDataFromViewModel()

        binding.btnFavoriteFrgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favoritesFragment)
        }

        binding.fullNameFrgProfile.text =
            getString(R.string.full_name_frg_profile, userModel.firstName, userModel.lastName)
        binding.phoneNumberFrgProfile.text = userModel.phoneNumber

        binding.btnExitFrgProfile.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.clearTable()
                productDao.clearTable()
            }
            restartActivity()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    private fun getDataFromViewModel() {
        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            setupData(productList)
        }
    }

    private fun setupData(productList: List<Product>) {
        binding.productCountFrgProfile.text = resources.getString(
            R.string.count_item,
            productList.size.toString(),
            selectFormCountFavoriteProducts(productList.size)
        )
    }
}
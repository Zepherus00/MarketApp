package com.example.storeapp.presentation.menu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storeapp.R
import com.example.storeapp.databinding.FragmentProfileBinding
import com.example.storeapp.presentation.utilities.restartActivity
import com.example.storeapp.presentation.utilities.selectFormCountFavoriteProducts
import com.example.storeapp.userModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromViewModel()
        setData()
        setListeners()
    }

    private fun getDataFromViewModel() {
        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            binding.productCountFrgProfile.text = resources.getString(
                R.string.count_item,
                productList.size.toString(),
                selectFormCountFavoriteProducts(productList.size)
            )
        }
    }

    private fun setData() {
        binding.fullNameFrgProfile.text =
            getString(R.string.full_name_frg_profile, userModel.firstName, userModel.lastName)
        binding.phoneNumberFrgProfile.text = userModel.phoneNumber
    }

    private fun setListeners() {
        binding.btnFavoriteFrgProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favoritesFragment)
        }

        binding.btnExitFrgProfile.setOnClickListener {
            viewModel.exitFromApp()
            restartActivity()
        }
    }
}
package com.example.storeapp.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.domain.model.SaveUserModel
import com.example.storeapp.R
import com.example.storeapp.databinding.FragmentRegistrationBinding
import com.example.storeapp.presentation.utilities.AppOnTextChangedWatcher
import com.example.storeapp.presentation.utilities.makeDisabled
import com.example.storeapp.presentation.utilities.setupPhoneMask
import com.example.storeapp.presentation.utilities.setupUnderlineForText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

const val NAME = "name"
const val LAST_NAME = "lastName"

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel = viewModel<RegistrationViewModel>()

    private val cyrillicPattern: Pattern = Pattern.compile("[А-Яа-я]*")
    private var isNameCorrect = false
    private var isLastNameCorrect = false
    private var isPhoneCorrect = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPhoneMask(binding.phoneFrgRegistration)
        setupUnderlineForText(binding.txtLoyalProgramFrgRegistration)
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            phoneFrgRegistration.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus)
                    phoneFrgRegistration.hint = getString(R.string.mask_phone_frg_registration)
                else
                    phoneFrgRegistration.hint = getString(R.string.text_phone_frg_registration)
            }

            nameFrgRegistration.addTextChangedListener(AppOnTextChangedWatcher { inputName ->
                setupInputListener(inputName.toString(), NAME, layoutNameFrgRegistration)
            })

            lastNameFrgRegistration.addTextChangedListener(AppOnTextChangedWatcher { inputLastName ->
                setupInputListener(
                    inputLastName.toString(),
                    LAST_NAME,
                    layoutLastNameFrgRegistration
                )
            })

            phoneFrgRegistration.addTextChangedListener(AppOnTextChangedWatcher { inputPhone ->
                if (inputPhone.toString().isNotBlank()) {
                    isPhoneCorrect = true
                    changeBtnEnterEnabled()
                    layoutPhoneFrgRegistration.isErrorEnabled = false
                } else {
                    isPhoneCorrect = false
                    changeBtnEnterEnabled()
                    layoutPhoneFrgRegistration.error =
                        getString(R.string.incorrect_input_frg_registration)
                }
            })

            btnEnterFrgRegistration.setOnClickListener {
                val param = SaveUserModel(
                    firstName = nameFrgRegistration.text.toString(),
                    lastName = lastNameFrgRegistration.text.toString(),
                    phoneNumber = phoneFrgRegistration.text.toString()
                )
                viewModel.value.addNewUser(param)
                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
            }
        }
    }

    private fun setupInputListener(input: String, inputType: String, view: TextInputLayout) {
        if (input.isNotBlank()) {
            if (isInputCyrillicValid(input)) {
                setInputType(inputType, true)
                changeBtnEnterEnabled()
                view.isErrorEnabled = false
            } else {
                setInputType(inputType, false)
                changeBtnEnterEnabled()
                view.error = getString(R.string.incorrect_input_frg_registration)
            }
        } else {
            binding.btnEnterFrgRegistration.makeDisabled()
            view.isErrorEnabled = false
        }
    }

    private fun isInputCyrillicValid(input: String): Boolean {
        return cyrillicPattern.matcher(input).matches()
    }

    private fun setInputType(inputType: String, isTrue: Boolean) {
        when (inputType) {
            NAME -> isNameCorrect = isTrue
            else -> isLastNameCorrect = isTrue
        }
    }

    private fun changeBtnEnterEnabled() {
        binding.btnEnterFrgRegistration.isEnabled =
            isNameCorrect && isLastNameCorrect && isPhoneCorrect
    }
}
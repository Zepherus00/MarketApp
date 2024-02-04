package com.example.storeapp.presentation.start

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storeapp.R
import com.example.storeapp.data.Repository
import com.example.storeapp.databinding.FragmentRegistrationBinding
import com.example.storeapp.utilities.AppOnTextChangedWatcher
import com.example.storeapp.utilities.makeDisabled
import com.example.storeapp.utilities.makeEnabled
import com.google.android.material.textfield.TextInputLayout
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.util.regex.Pattern

const val NAME = "name"
const val LAST_NAME = "lastName"

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val repository = Repository()
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
        setupPhoneMask()
        setupUnderlineForText()
        setListeners()
    }

    private fun setupPhoneMask() {
        val slots = UnderscoreDigitSlotsParser().parseSlots("+7 ___ ___ __ __")
        val mask = MaskImpl.createTerminated(slots)
        mask.isHideHardcodedHead = true
        mask.isForbidInputWhenFilled = true
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(binding.phoneFrgRegistration)
    }

    private fun setupUnderlineForText() {
        binding.txtLoyalProgramFrgRegistration.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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
                    changeBtnEnabled()
                    layoutPhoneFrgRegistration.isErrorEnabled = false
                } else {
                    isPhoneCorrect = false
                    changeBtnEnabled()
                    layoutPhoneFrgRegistration.error =
                        getString(R.string.incorrect_input_frg_registration)
                }
            })

            btnEnterFrgRegistration.setOnClickListener {
                repository.insertUser(
                    nameFrgRegistration.text.toString(),
                    lastNameFrgRegistration.text.toString(),
                    phoneFrgRegistration.text.toString()
                )
                findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
            }
        }
    }

    private fun setupInputListener(input: String, inputType: String, view: TextInputLayout) {
        if (input.isNotBlank()) {
            if (isInputValid(input)) {
                setInputType(inputType, true)
                changeBtnEnabled()
                view.isErrorEnabled = false
            } else {
                setInputType(inputType, false)
                changeBtnEnabled()
                view.error = getString(R.string.incorrect_input_frg_registration)
            }
        } else {
            binding.btnEnterFrgRegistration.makeDisabled()
            view.isErrorEnabled = false
        }
    }

    private fun isInputValid(input: String): Boolean {
        return cyrillicPattern.matcher(input).matches()
    }

    private fun setInputType(inputType: String, isTrue: Boolean) {
        when (inputType) {
            NAME -> isNameCorrect = isTrue
            else -> isLastNameCorrect = isTrue
        }
    }

    private fun changeBtnEnabled() {
        if (isNameCorrect && isLastNameCorrect && isPhoneCorrect)
            binding.btnEnterFrgRegistration.makeEnabled()
        else
            binding.btnEnterFrgRegistration.makeDisabled()
    }
}
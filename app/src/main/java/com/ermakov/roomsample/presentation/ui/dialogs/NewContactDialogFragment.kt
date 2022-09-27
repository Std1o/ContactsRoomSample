package com.ermakov.roomsample.presentation.ui.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ermakov.roomsample.R
import com.ermakov.roomsample.databinding.DialogNewContactBinding
import com.ermakov.roomsample.domain.ContactState
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.presentation.viewmodel.NewContactViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewContactDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogNewContactBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewContactViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNewContactBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)

        with(binding) {
            btnAdd.setOnClickListener {
                addContact(name.text.toString(), phone.text.toString())
            }
            nameLayout.editText?.doOnTextChanged { _, _, _, _ ->
                nameLayout.error = null
            }
            phoneLayout.editText?.doOnTextChanged { _, _, _, _ ->
                phoneLayout.error = null
            }
        }
    }

    private fun addContact(name: String, phone: String) {
        lifecycleScope.launch {
            viewModel.addContact(Contact(name, phone)).collect {
                if (it is ContactState.EmptyName) {
                    binding.nameLayout.error = getString(R.string.required_field)
                } else if (it is ContactState.EmptyPhone) {
                    binding.phoneLayout.error = getString(R.string.required_field)
                } else if (it is ContactState.Success) {
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun newInstance(): NewContactDialogFragment = NewContactDialogFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import com.ermakov.roomsample.R
import com.ermakov.roomsample.databinding.DialogEditContactBinding
import com.ermakov.roomsample.domain.ContactState
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.presentation.viewmodel.EditContactViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditContactDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogEditContactBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<EditContactViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditContactBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        val contact = arguments?.getParcelable<Contact>(ARG_CONTACT)!!

        with(binding) {
            name.setText(contact.name)
            phone.setText(contact.phone)
            btnAdd.setOnClickListener {
                val newContact = Contact(name.text.toString(), phone.text.toString())
                newContact.id = contact.id
                editContact(newContact)
            }
            nameLayout.editText?.doOnTextChanged { _, _, _, _ ->
                nameLayout.error = null
            }
            phoneLayout.editText?.doOnTextChanged { _, _, _, _ ->
                phoneLayout.error = null
            }
        }
    }

    private fun editContact(contact: Contact) {
        viewModel.updateContact(contact).observe(viewLifecycleOwner) {
            if (it is ContactState.EmptyName) {
                binding.nameLayout.error = getString(R.string.required_field)
            } else if (it is ContactState.EmptyPhone) {
                binding.phoneLayout.error = getString(R.string.required_field)
            } else if (it is ContactState.Success) {
                dismiss()
            }
        }
    }

    companion object {
        private const val ARG_CONTACT= "contact"

        fun newInstance(contact: Contact): EditContactDialogFragment =
            EditContactDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONTACT, contact)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
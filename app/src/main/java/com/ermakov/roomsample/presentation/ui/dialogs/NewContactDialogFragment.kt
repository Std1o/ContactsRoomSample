package com.ermakov.roomsample.presentation.ui.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ermakov.roomsample.databinding.DialogNewContactBinding
import com.ermakov.roomsample.presentation.ui.activity.MainActivity.Companion.ARG_NAME
import com.ermakov.roomsample.presentation.ui.activity.MainActivity.Companion.ARG_PHONE
import com.ermakov.roomsample.presentation.ui.activity.MainActivity.Companion.KEY_ADD_OR_EDIT_CONTACT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewContactDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogNewContactBinding? = null
    private val binding get() = _binding!!

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
                val bundle = Bundle()
                bundle.putString(ARG_NAME, name.text.toString())
                bundle.putString(ARG_PHONE, phone.text.toString())
                requireActivity()
                    .supportFragmentManager
                    .setFragmentResult(KEY_ADD_OR_EDIT_CONTACT, bundle)
                dismiss()
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
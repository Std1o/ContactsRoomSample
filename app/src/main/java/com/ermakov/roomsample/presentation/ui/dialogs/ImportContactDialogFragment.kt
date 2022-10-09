package com.ermakov.roomsample.presentation.ui.dialogs

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ermakov.roomsample.databinding.DialogImportContactBinding
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.presentation.ui.adapter.ContactsImportAdapter
import com.ermakov.roomsample.presentation.viewmodel.ImportContactViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImportContactDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogImportContactBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ImportContactViewModel>()
    private var readContactsGranted = false
    private lateinit var adapter: ContactsImportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogImportContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        expandBottomSheet()
        initRV()
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            readContactsGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_READ_CONTACTS
            )
        }
        if (readContactsGranted) {
            viewModel.loadContacts(requireContext().contentResolver)
            viewModel.contacts.observe(this) { contacts ->
                adapter.submitData(contacts)
            }
        }
    }

    private fun initRV() {
        val recyclerView = binding.rv
        adapter = ContactsImportAdapter {
            addContact(it)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addContact(contact: Contact) {
        viewModel.addContact(contact).observe(viewLifecycleOwner) {
            dismiss()
        }
    }

    private fun expandBottomSheet() {
        val rectangle = Rect()
        val bottomSheet = requireView().parent as View
        bottomSheet.layoutParams.height =
            getScreenHeight(requireActivity()) - rectangle.top
        requireView().post {
            val parent = requireView().parent as View
            val params =
                parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = (behavior as BottomSheetBehavior<*>?)!!
            bottomSheetBehavior.peekHeight = requireView().measuredHeight
        }
    }

    private fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    companion object {
        fun newInstance(): ImportContactDialogFragment = ImportContactDialogFragment()
        private const val REQUEST_CODE_READ_CONTACTS = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
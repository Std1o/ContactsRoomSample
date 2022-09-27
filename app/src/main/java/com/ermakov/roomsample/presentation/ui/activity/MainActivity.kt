package com.ermakov.roomsample.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ermakov.roomsample.R
import com.ermakov.roomsample.model.Contact
import com.ermakov.roomsample.presentation.ui.adapter.ContactsListAdapter
import com.ermakov.roomsample.presentation.ui.dialogs.NewContactDialogFragment
import com.ermakov.roomsample.presentation.viewmodel.ContactsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val wordViewModel by viewModels<ContactsViewModel>()

    companion object {
        const val KEY_ADD_OR_EDIT_CONTACT = "key_add_or_edit_contact"
        const val ARG_NAME = "arg_name"
        const val ARG_PHONE = "arg_phone"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = ContactsListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.bntAdd)
        fab.setOnClickListener {
            NewContactDialogFragment
                .newInstance()
                .show(supportFragmentManager, "new_contact")
            setFragmentResultListeners()
        }
    }

    private fun setFragmentResultListeners() {
        supportFragmentManager.setFragmentResultListener(KEY_ADD_OR_EDIT_CONTACT, this) { _, bundle ->
            val name = bundle.getString(ARG_NAME) ?: return@setFragmentResultListener
            val phone = bundle.getString(ARG_PHONE) ?: return@setFragmentResultListener
            val contact = Contact(name, phone)
            wordViewModel.insert(contact)
        }
    }
}
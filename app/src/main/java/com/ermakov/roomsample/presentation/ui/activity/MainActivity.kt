package com.ermakov.roomsample.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ermakov.roomsample.R
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.presentation.ui.adapter.ContactsListAdapter
import com.ermakov.roomsample.presentation.ui.dialogs.EditContactDialogFragment
import com.ermakov.roomsample.presentation.ui.dialogs.ImportContactDialogFragment
import com.ermakov.roomsample.presentation.ui.dialogs.NewContactDialogFragment
import com.ermakov.roomsample.presentation.viewmodel.ContactsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ContactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = ContactsListAdapter(object : ContactsListAdapter.ClickListener {
            override fun onClick(phone: String) {
                println(phone)
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                startActivity(intent)
            }

            override fun onMenuClick(view: View, contact: Contact) {
                showPopup(view, contact)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allContacts.observe(this) { contacts ->
            contacts.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.bntAdd)
        fab.setOnClickListener {
            NewContactDialogFragment
                .newInstance()
                .show(supportFragmentManager, "new_contact")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_import_contact -> {
                ImportContactDialogFragment
                    .newInstance()
                    .show(supportFragmentManager, "import_contact")
            }
        }
        return true
    }

    private fun showPopup(v: View, contact: Contact) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.contact_context_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit_contact -> {
                    EditContactDialogFragment
                        .newInstance(contact)
                        .show(supportFragmentManager, "edit_contact")
                }
                R.id.action_delete_contact -> {
                    confirmDeletion(contact)
                }
            }
            true
        }
    }

    private fun confirmDeletion(contact: Contact) {
        MaterialAlertDialogBuilder(this)
            .setMessage(R.string.delete_contact_request)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.delete(contact)
            }
            .show()
    }
}
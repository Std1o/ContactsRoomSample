package com.ermakov.roomsample.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ermakov.roomsample.R
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.presentation.ui.adapter.ContactsListAdapter
import com.ermakov.roomsample.presentation.ui.dialogs.NewContactDialogFragment
import com.ermakov.roomsample.presentation.viewmodel.ContactsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ContactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = ContactsListAdapter { view, contact ->
            showPopup(view, contact)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allWords.observe(this) { contacts ->
            contacts.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.bntAdd)
        fab.setOnClickListener {
            NewContactDialogFragment
                .newInstance()
                .show(supportFragmentManager, "new_contact")
        }
    }

    private fun showPopup(v: View, contact: Contact) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.contact_context_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit_contact -> {
                    Toast.makeText(this, "AJAJJA", Toast.LENGTH_LONG).show()
                }
                R.id.action_delete_contact -> {
                    //confirmAction(R.string.delete_request) { _, _ ->
                    //    deleteParticipant(course, participant.id)
                    //}
                }
            }
            true
        }
    }
}
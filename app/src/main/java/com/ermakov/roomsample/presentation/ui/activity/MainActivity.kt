package com.ermakov.roomsample.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        val adapter = ContactsListAdapter()
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
}
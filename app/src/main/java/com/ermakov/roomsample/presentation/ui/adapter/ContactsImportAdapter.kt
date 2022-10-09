package com.ermakov.roomsample.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ermakov.roomsample.R
import com.ermakov.roomsample.databinding.ItemContactBinding
import com.ermakov.roomsample.databinding.ItemContactImportBinding
import com.ermakov.roomsample.domain.model.Contact

class ContactsImportAdapter(private val listener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactsImportAdapter.ContactsViewHolder>() {

    private var dataList: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ItemContactImportBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    fun submitData(dataList: MutableList<Contact>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = dataList[position]
        val name = contact.name
        val phone = contact.phone
        with(holder) {
            binding.textView.text = itemView.context.getString(R.string.item_contact, name, phone)
            itemView.setOnClickListener {
                listener.invoke(contact)
            }
        }
    }

    inner class ContactsViewHolder(val binding: ItemContactImportBinding) :
        RecyclerView.ViewHolder(binding.root)
}
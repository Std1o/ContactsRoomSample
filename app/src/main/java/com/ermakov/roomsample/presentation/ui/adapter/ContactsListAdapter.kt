package com.ermakov.roomsample.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ermakov.roomsample.R
import com.ermakov.roomsample.databinding.ItemContactBinding
import com.ermakov.roomsample.domain.model.Contact

class ContactsListAdapter(private var listener: (View, Contact) -> Unit) :
    RecyclerView.Adapter<ContactsListAdapter.ContactsViewHolder>() {

    private var dataList: List<Contact> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ItemContactBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    fun submitList(dataList: List<Contact>) {
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
            binding.btnMenu.setOnClickListener {
                listener.invoke(binding.btnMenu, contact)
            }
        }
    }

    inner class ContactsViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)
}
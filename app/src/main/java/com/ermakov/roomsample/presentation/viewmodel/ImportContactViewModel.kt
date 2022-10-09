package com.ermakov.roomsample.presentation.viewmodel

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.*
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.data.Repository
import com.ermakov.roomsample.domain.ContactState
import com.ermakov.roomsample.domain.usecases.AddContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase
) : ViewModel() {

    val contacts: MutableLiveData<MutableList<Contact>> by lazy {
        MutableLiveData<MutableList<Contact>>()
    }

    fun addContact(contact: Contact): LiveData<ContactState> {
        val contactState: MutableLiveData<ContactState> = MutableLiveData()
        viewModelScope.launch {
            contactState.value = addContactUseCase(contact)
        }
        return contactState
    }

    private fun contains(item: Contact, dataList: MutableList<Contact>): Boolean {
        return dataList.any { (it.name == item.name) }
    }

    fun loadContacts(cr: ContentResolver) {
        val localContacts = mutableListOf<Contact>()
        contacts.value = mutableListOf()
        val cur = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cur?.count ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val idIndex = cur.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIndex = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val hasPhoneNumberIndex = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                val id = cur.getString(idIndex)
                val name = cur.getString(nameIndex)
                if (cur.getInt(hasPhoneNumberIndex) > 0) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    while (pCur!!.moveToNext()) {
                        val phoneIndex = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val phone = pCur.getString(phoneIndex)
                        val contact = Contact(name, phone)
                        if (!contains(contact, localContacts)) localContacts.add(contact)
                    }
                    pCur.close()
                }
            }
        }
        cur?.close()
        contacts.value = localContacts
    }
}
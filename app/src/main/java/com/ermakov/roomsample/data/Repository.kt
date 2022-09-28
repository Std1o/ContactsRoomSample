package com.ermakov.roomsample.data

import androidx.annotation.WorkerThread
import com.ermakov.roomsample.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val contactsDao: ContactsDao)  {

    val allContacts: Flow<List<Contact>> = contactsDao.getAlphabetizedContacts()

    suspend fun addContact(contact: Contact) {
        contactsDao.insert(contact)
    }

    suspend fun updateContact(contact: Contact) {
        contactsDao.update(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        contactsDao.delete(contact)
    }
}
package com.ermakov.roomsample.data

import androidx.annotation.WorkerThread
import com.ermakov.roomsample.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val contactsDao: ContactsDao)  {

    val allContacts: Flow<List<Contact>> = contactsDao.getAlphabetizedContacts()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addContact(contact: Contact) {
        contactsDao.insert(contact)
    }
}
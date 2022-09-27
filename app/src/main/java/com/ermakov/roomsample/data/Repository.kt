package com.ermakov.roomsample.data

import androidx.annotation.WorkerThread
import com.ermakov.roomsample.model.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class Repository @Inject constructor(private val contactsDao: ContactsDao)  {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allContacts: Flow<List<Contact>> = contactsDao.getAlphabetizedContacts()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Contact) {
        contactsDao.insert(word)
    }
}
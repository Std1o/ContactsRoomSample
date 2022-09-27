package com.ermakov.roomsample.presentation.viewmodel

import androidx.lifecycle.*
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val allWords: LiveData<List<Contact>> = repository.allContacts.asLiveData()
}
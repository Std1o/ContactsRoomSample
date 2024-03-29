package com.ermakov.roomsample.presentation.viewmodel

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
class NewContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase
) : ViewModel() {

    fun addContact(contact: Contact): LiveData<ContactState> {
        val contactState: MutableLiveData<ContactState> = MutableLiveData()
        viewModelScope.launch {
            contactState.value = addContactUseCase(contact)
        }
        return contactState
    }
}
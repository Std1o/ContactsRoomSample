package com.ermakov.roomsample.presentation.viewmodel

import androidx.lifecycle.*
import com.ermakov.roomsample.domain.model.Contact
import com.ermakov.roomsample.data.Repository
import com.ermakov.roomsample.domain.ContactState
import com.ermakov.roomsample.domain.usecases.AddContactUseCase
import com.ermakov.roomsample.domain.usecases.UpdateContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditContactViewModel @Inject constructor(
    private val updateContactUseCase: UpdateContactUseCase
) : ViewModel() {

    fun updateContact(contact: Contact): LiveData<ContactState> {
        val contactState: MutableLiveData<ContactState> = MutableLiveData()
        viewModelScope.launch {
            contactState.value = updateContactUseCase(contact)
        }
        return contactState
    }
}
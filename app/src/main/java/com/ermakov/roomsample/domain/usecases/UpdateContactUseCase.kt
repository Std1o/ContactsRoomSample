package com.ermakov.roomsample.domain.usecases

import com.ermakov.roomsample.data.Repository
import com.ermakov.roomsample.domain.ContactState
import com.ermakov.roomsample.domain.model.Contact
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(contact: Contact): ContactState {
        return if (contact.name.isEmpty()) {
            ContactState.EmptyName
        } else if(contact.phone.isEmpty()) {
            ContactState.EmptyPhone
        } else {
            repository.updateContact(contact)
            ContactState.Success
        }
    }
}
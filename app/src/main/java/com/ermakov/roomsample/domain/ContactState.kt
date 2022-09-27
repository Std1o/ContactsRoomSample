package com.ermakov.roomsample.domain

import androidx.annotation.StringRes

sealed class ContactState {
    object Initial : ContactState()
    object Success : ContactState()
    object EmptyName : ContactState()
    object EmptyPhone : ContactState()
}
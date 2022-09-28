package com.ermakov.roomsample.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Contact(val name: String, val phone: String) : Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
package com.ermakov.roomsample.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
class Contact(val name: String, val phone: String) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
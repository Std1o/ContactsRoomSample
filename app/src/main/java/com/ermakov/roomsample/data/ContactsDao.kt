package com.ermakov.roomsample.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ermakov.roomsample.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    fun getAlphabetizedContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Contact)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}
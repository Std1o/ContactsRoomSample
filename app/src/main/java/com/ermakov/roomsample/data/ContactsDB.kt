package com.ermakov.roomsample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ermakov.roomsample.domain.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactsDB : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val wordDao = database.contactsDao()
                    wordDao.deleteAll()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ContactsDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ContactsDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDB::class.java,
                    "contact_database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
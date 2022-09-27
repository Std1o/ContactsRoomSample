package com.ermakov.roomsample.di

import android.content.Context
import com.ermakov.roomsample.data.ContactsDao
import com.ermakov.roomsample.data.Repository
import com.ermakov.roomsample.data.ContactsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ContactsDB {
        return ContactsDB.getDatabase(appContext, CoroutineScope(SupervisorJob()))
    }

    @Provides
    @Singleton
    fun provideDao(database: ContactsDB) = database.contactsDao()

    @Singleton
    @Provides
    fun provideRepository(contactsDao: ContactsDao) = Repository(contactsDao)
}
package com.ermakov.roomsample.di

import android.content.Context
import com.ermakov.roomsample.data.WordDao
import com.ermakov.roomsample.data.WordRepository
import com.ermakov.roomsample.data.WordRoomDatabase
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): WordRoomDatabase {
        return WordRoomDatabase.getDatabase(appContext, CoroutineScope(SupervisorJob()))
    }

    @Provides
    @Singleton
    fun provideDao(database: WordRoomDatabase) = database.wordDao()

    @Singleton
    @Provides
    fun provideRepository(dao: WordDao) = WordRepository(dao)
}
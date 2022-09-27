package com.ermakov.roomsample.presentation.viewmodel

import androidx.lifecycle.*
import com.ermakov.roomsample.model.Contact
import com.ermakov.roomsample.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Contact>> = repository.allWords.asLiveData()

    fun insert(word: Contact) = viewModelScope.launch {
        repository.insert(word)
    }
}
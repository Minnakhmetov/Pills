package com.example.pills

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pills.database.PillsDatabase
import com.example.pills.database.getDatabase
import com.example.pills.models.Pill
import kotlinx.coroutines.*
import timber.log.Timber

class AddPillViewModel(context: Context) : ViewModel() {
    private val database = getDatabase(context)

    private val repository = PillsRepository(database)

    private val _duplicateFoundEvent = MutableLiveData<Boolean>()

    val duplicateFoundEvent: LiveData<Boolean>
        get() = _duplicateFoundEvent

    private val _navigateBackToList = MutableLiveData<Boolean>()

    val navigateBackToList: LiveData<Boolean>
        get() = _navigateBackToList

    private val job = Job()

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    fun onDoneButton(name: String, doses: Int) {
        coroutineScope.launch {
            if (repository.pillExists(name)) {
                _duplicateFoundEvent.value = true
            } else {
                repository.insert(
                    Pill(
                        name,
                        System.currentTimeMillis(),
                        doses,
                        0
                    )
                )

                _navigateBackToList.value = true
            }
        }
    }

    fun onDuplicateEventHandled() {
        _duplicateFoundEvent.value = false
    }

    fun onNavigationToListCompleted() {
        _navigateBackToList.value = false
    }

    override fun onCleared() {
        job.cancel()
    }
}
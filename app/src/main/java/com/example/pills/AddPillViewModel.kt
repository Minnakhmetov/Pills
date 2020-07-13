package com.example.pills

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.pills.database.PillsDatabase
import com.example.pills.database.getDatabase
import com.example.pills.models.Pill
import kotlinx.coroutines.*
import timber.log.Timber

class AddPillViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PillsRepository(
        getDatabase(application),
        (application as PillApplication).applicationScope
    )

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
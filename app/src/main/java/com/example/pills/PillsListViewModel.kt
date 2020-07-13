package com.example.pills

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.pills.database.getDatabase
import com.example.pills.models.Pill
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class Filter {
    NONE, HIDE_COMPLETED, NOT_COMPLETED_FIRST
}

class PillsListViewModel(application: Application): AndroidViewModel(application) {

    private val _navigateToAddPill = MutableLiveData<Boolean>()

    private val repository = PillsRepository(getDatabase(
        application),
        (application as PillApplication).applicationScope
    )

    var filter = Filter.NONE
        set(value) {
            field = value

            pillsList = when (value) {
                Filter.NONE -> repository.getPillsList()
                Filter.HIDE_COMPLETED -> Transformations.map(repository.getPillsList()) {
                    it.filter { pill -> pill.currentDoses < pill.requiredDoses }
                }
                Filter.NOT_COMPLETED_FIRST -> Transformations.map(repository.getPillsList()) {
                    it.sortedBy { pill -> pill.requiredDoses == pill.currentDoses }
                }
            }
        }

    var pillsList = repository.getPillsList()
        private set

    val navigateToAddPill: LiveData<Boolean>
        get() = _navigateToAddPill

    fun onFloatButtonClicked() {
        _navigateToAddPill.value = true
    }

    fun navigationToAddPillCompleted() {
        _navigateToAddPill.value = false
    }

    fun onPillDeletion(pill: Pill) {
        repository.delete(pill)
    }

    fun onDoseIncrease(pill: Pill) {
        if (pill.currentDoses < pill.requiredDoses) {
            repository.update(Pill(
                name = pill.name,
                lastModified = pill.lastModified,
                currentDoses = pill.currentDoses + 1,
                requiredDoses = pill.requiredDoses
            ))
        }
    }

    fun onDoseDecrease(pill: Pill) {
        if (pill.currentDoses > 0) {
            repository.update(Pill(
                name = pill.name,
                lastModified = pill.lastModified,
                currentDoses = pill.currentDoses - 1,
                requiredDoses = pill.requiredDoses
            ))
        }
    }
}
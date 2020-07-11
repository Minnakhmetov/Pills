package com.example.pills

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PillsListViewModel: ViewModel() {

    private val _navigateToAddPill = MutableLiveData<Boolean>()

    val navigateToAddPill: LiveData<Boolean>
        get() = _navigateToAddPill

    fun onFloatButtonClicked() {
        _navigateToAddPill.value = true
    }

    fun navigationToAddPillCompleted() {
        _navigateToAddPill.value = false
    }
}
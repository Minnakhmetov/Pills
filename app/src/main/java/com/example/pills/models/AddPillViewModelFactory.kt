package com.example.pills.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pills.AddPillViewModel
import java.lang.IllegalArgumentException

class AddPillViewModelFactory(val appContext: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPillViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPillViewModel(appContext) as T
        }
        throw IllegalArgumentException("unknown ViewModel")
    }
}
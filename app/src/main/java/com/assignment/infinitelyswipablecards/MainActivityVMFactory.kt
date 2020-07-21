package com.assignment.infinitelyswipablecards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.infinitelyswipablecards.network.Repository
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * Created by Divya Gupta.
 */
@Suppress("UNCHECKED_CAST")
class MainActivityVMFactory @Inject constructor(val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
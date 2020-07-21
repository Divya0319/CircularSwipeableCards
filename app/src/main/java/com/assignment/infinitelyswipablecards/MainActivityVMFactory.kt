package com.assignment.infinitelyswipablecards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.infinitelyswipablecards.network.Repository
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * Created by Divya Gupta.
 */

/**
 * This is the factory class for creating ViewModel instance using repository instance. It is not needed to be created explicitly,
 * and ViewModel instance gets automatically created in cases when ViewModel does not accept any parameters in its constructor.
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
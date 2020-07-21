package com.assignment.infinitelyswipablecards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.infinitelyswipablecards.network.ApiResponse
import com.assignment.infinitelyswipablecards.network.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Divya Gupta.
 */

/**
 * This ViewModel class runs the api call method of Retrofit in background, and provides the response LiveData,
 * which can be listened for changes, and relevant rendering can be done in UI
 */
class MainActivityViewModel(val repository: Repository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val responseCardsAPI = MutableLiveData<ApiResponse>()
    val cardsApiResponse: LiveData<ApiResponse> get() = responseCardsAPI // this livedata is made public, to be able to be listened onto.

    fun hitCardsApi() {
        disposable.add(repository.executeCardsDataApiCall()
            .subscribeOn(Schedulers.io()) // make the call in I/O thread
            .observeOn(AndroidSchedulers.mainThread()) // listen to changes in UI thread
            .doOnSubscribe { responseCardsAPI.value = ApiResponse.loading() }
            .subscribe(
                { result -> responseCardsAPI.value = ApiResponse.success(result) }, // subscribe for the success response
                { error -> responseCardsAPI.value = ApiResponse.error(error) } // subscribe for error response
            ))
    }


    override fun onCleared() {
        disposable.clear()
    }
}
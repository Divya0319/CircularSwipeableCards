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
class MainActivityViewModel(val repository: Repository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val responseCardsAPI = MutableLiveData<ApiResponse>()
    val cardsApiResponse: LiveData<ApiResponse> get() = responseCardsAPI

    fun hitCardsApi() {
        disposable.add(repository.executeCardsDataApiCall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { responseCardsAPI.value = ApiResponse.loading() }
            .subscribe(
                { result -> responseCardsAPI.value = ApiResponse.success(result) },
                { error -> responseCardsAPI.value = ApiResponse.error(error) }
            ))
    }


    override fun onCleared() {
        disposable.clear()
    }
}
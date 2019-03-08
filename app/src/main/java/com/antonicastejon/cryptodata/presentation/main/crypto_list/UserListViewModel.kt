package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.di.SCHEDULER_IO
import com.antonicastejon.cryptodata.di.SCHEDULER_MAIN_THREAD
import com.antonicastejon.cryptodata.domain.LIMIT_USER_LIST
import com.antonicastejon.cryptodata.domain.UserListUseCases
import com.antonicastejon.cryptodata.domain.UserViewModel
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private val TAG = UserListViewModel::class.java.name

class UserListViewModel
@Inject constructor(private val cryptoListUseCases: UserListUseCases,
                    @Named(SCHEDULER_IO) val subscribeOnScheduler:Scheduler,
                    @Named(SCHEDULER_MAIN_THREAD) val observeOnScheduler: Scheduler) : ViewModel() {

    val stateLiveData =  MutableLiveData<UserListState>()

    init {
        stateLiveData.value = DefaultState1(0, false, emptyList())
    }

    fun updateCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = if (pageNum == 0)
            LoadingState1(pageNum, false, obtainCurrentData())
        else
            PaginatingState1(pageNum, false, obtainCurrentData())
        getCryptoList(pageNum)
    }

    fun resetCryptoList() {
        val pageNum = 0
        stateLiveData.value = LoadingState1(pageNum, false, emptyList())
        updateCryptoList()
    }

    fun restoreCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = DefaultState1(pageNum, false, obtainCurrentData())
    }

    @SuppressLint("CheckResult")
    private fun getCryptoList(page:Int) {
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(this::onCryptoListReceived, this::onError)
    }

    private fun onCryptoListReceived(cryptoList: List<UserViewModel>) {
        val currentCryptoList = obtainCurrentData().toMutableList()
        val currentPageNum = obtainCurrentPageNum() + 1
        val areAllItemsLoaded = cryptoList.size < LIMIT_USER_LIST
        currentCryptoList.addAll(cryptoList)
        stateLiveData.value = DefaultState1(currentPageNum, areAllItemsLoaded, currentCryptoList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = ErrorState1(error.message ?: "", pageNum, obtainCurrentLoadedAllItems(), obtainCurrentData())
    }

    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false

}
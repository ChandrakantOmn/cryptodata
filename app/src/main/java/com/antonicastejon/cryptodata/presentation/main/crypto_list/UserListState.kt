package com.antonicastejon.cryptodata.presentation.main.crypto_list

import com.antonicastejon.cryptodata.domain.UserViewModel

/**
 * Created by Antoni Castejón
 * 20/01/2018.
 */
sealed class UserListState {
    abstract val pageNum: Int
    abstract val loadedAllItems: Boolean
    abstract val data: List<UserViewModel>
}

data class DefaultState1(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<UserViewModel>) : UserListState()
data class LoadingState1(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<UserViewModel>) : UserListState()
data class PaginatingState1(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<UserViewModel>) : UserListState()
data class ErrorState1(val errorMessage: String, override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<UserViewModel>) : UserListState()
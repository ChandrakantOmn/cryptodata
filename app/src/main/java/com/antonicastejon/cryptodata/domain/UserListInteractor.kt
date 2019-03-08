package com.antonicastejon.cryptodata.domain

import com.antonicastejon.cryptodata.model.User
import com.antonicastejon.cryptodata.model.UserMarketCapApi
import com.antonicastejon.cryptodata.model.UserMarketCapRepository
import io.reactivex.Single

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

const val LIMIT_USER_LIST = 20

class UserListInteractor(private val coinMarketCapRepository: UserMarketCapRepository) : UserListUseCases {

    override fun getCryptoListBy(page: Int): Single<List<UserViewModel>> {
        return coinMarketCapRepository.getUserList(page, LIMIT_USER_LIST)
                .map { cryptos -> cryptos.map(cryptoViewModelMapper) }
    }

    private val cryptoViewModelMapper: (User) -> UserViewModel = { user ->
        val userViewModel = UserViewModel()
        userViewModel
    }
}
package com.antonicastejon.cryptodata.model

import io.reactivex.Single

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
class UserCapDownloader(private val coinMarketCapApi: UserMarketCapApi) : UserMarketCapApi {
    override fun userList(start: Int, limit: Int): Single<List<User>> =
            coinMarketCapApi.userList(start * limit, limit)


}
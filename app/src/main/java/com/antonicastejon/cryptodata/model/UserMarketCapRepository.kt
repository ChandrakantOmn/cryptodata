package com.antonicastejon.cryptodata.model

import io.reactivex.Single

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
interface UserMarketCapRepository {

    fun getUserList(page:Int, limit:Int) : Single<List<User>>
}
package com.antonicastejon.cryptodata.di

import com.antonicastejon.cryptodata.domain.CryptoListInteractor
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.UserListInteractor
import com.antonicastejon.cryptodata.domain.UserListUseCases
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import com.antonicastejon.cryptodata.model.UserMarketCapRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Antoni Castejón on 04/01/2018.
 */
@Module
class UseCasesModule {

    @Provides
    fun providesCryptoListUseCases(coinMarketCapRepository: CoinMarketCapRepository): CryptoListUseCases = CryptoListInteractor(coinMarketCapRepository)

    @Provides
    fun providesUserListUseCases(repository: UserMarketCapRepository): UserListUseCases = UserListInteractor(repository)
}
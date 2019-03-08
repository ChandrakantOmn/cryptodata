package com.antonicastejon.cryptodata.presentation.main.di

import com.antonicastejon.cryptodata.presentation.main.MainActivity
import com.antonicastejon.cryptodata.presentation.main.crypto_list.UserFragmentModule
import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Created by Antoni Castejón on 03/01/2018.
 */
@Subcomponent(modules = arrayOf(UserFragmentModule::class))
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
package com.antonicastejon.cryptodata.di

import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.presentation.main.crypto_list.CryptoListViewModel
import com.antonicastejon.cryptodata.presentation.main.crypto_list.UserListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


/**
 * Created by Antoni Castejón on 03/01/2018.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CryptoListViewModel::class)
    abstract fun bindCryptoListViewModel(viewModel: CryptoListViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(viewModel: CryptoListViewModel) : ViewModel
}
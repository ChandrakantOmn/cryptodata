package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by Antoni Castejón on 03/01/2018.
 */
@Subcomponent/*(modules = ...)*/
interface UserListFragmentSubcomponent: AndroidInjector<UserListFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<UserListFragment>()
}

@Module(subcomponents = arrayOf(UserListFragmentSubcomponent::class))
abstract class UserFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(UserListFragment::class)
    abstract fun bindUserListFragmentInjectorFactory(builder: UserListFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}
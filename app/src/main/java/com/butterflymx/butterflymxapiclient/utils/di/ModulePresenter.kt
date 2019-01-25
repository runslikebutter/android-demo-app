package com.butterflymx.butterflymxapiclient.utils.di

import com.butterflymx.butterflymxapiclient.signinsignup.login.LoginFragmentPresenter
import dagger.Module
import dagger.Provides

@Module
internal class ModulePresenter {

    @Provides
    fun loginFragmentPresenter(): LoginFragmentPresenter {
        return LoginFragmentPresenter()
    }
}

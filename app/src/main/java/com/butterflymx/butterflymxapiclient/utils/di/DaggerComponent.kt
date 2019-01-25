package com.butterflymx.butterflymxapiclient.utils.di

import com.butterflymx.butterflymxapiclient.signinsignup.login.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ModulePresenter::class))
interface DaggerComponent {

    fun injectLoginFragment(loginFragment: LoginFragment)
}

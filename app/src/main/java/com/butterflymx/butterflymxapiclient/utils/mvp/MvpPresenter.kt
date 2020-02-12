package com.butterflymx.butterflymxapiclient.utils.mvp

interface MvpPresenter<T : BaseView> {

    fun attachView(mvpView: T)

    fun viewIsReady()

    fun detachView()

    fun destroy()
}
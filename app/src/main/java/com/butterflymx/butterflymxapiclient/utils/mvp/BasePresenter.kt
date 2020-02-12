package com.butterflymx.butterflymxapiclient.utils.mvp

abstract class BasePresenter<T : BaseView> : MvpPresenter<T> {

    var view: T? = null
        private set

    protected val isViewAttached: Boolean
        get() = view != null

    override fun attachView(mvpView: T) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {}

    override fun viewIsReady() {}


}
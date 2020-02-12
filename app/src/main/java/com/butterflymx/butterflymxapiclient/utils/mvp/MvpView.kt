package com.butterflymx.butterflymxapiclient.utils.mvp

interface MvpView {
    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)

    fun onCompleteMainAction()

}

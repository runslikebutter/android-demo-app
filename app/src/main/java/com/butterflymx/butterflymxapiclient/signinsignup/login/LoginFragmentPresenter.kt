package com.butterflymx.butterflymxapiclient.signinsignup.login

import android.content.Context
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.mvp.BasePresenter
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.core.AuthData
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.RequestCallBack

class LoginFragmentPresenter : BasePresenter<BaseView>() {

    fun login(email: String, password: String) {
        view.showLoading()
        try {
            val authData = generateAuthData(email, password)
            BMXCore.getInstance(App.getContext()).signIn(authData)
        } catch (e: Exception) {
            if (isViewAttached) {
                view.showMessage("Error: ${e.message}")
                view.hideLoading()
            }
        }
    }

    private fun generateAuthData(email: String, password: String): AuthData {
        val authDataBuilder = AuthData.Builder()
        authDataBuilder.setCallBack(createCallBack())
        authDataBuilder.setEmail(email)
        authDataBuilder.setPassword(password)
        authDataBuilder.setSecretID(App.getContext().getString(R.string.SECRET_ID))
        authDataBuilder.setClientID(App.getContext().getString(R.string.CLIENT_ID))
        return authDataBuilder.build()
    }

    private fun createCallBack(): RequestCallBack {
        return object : RequestCallBack {
            override fun onSuccess() {
                if (isViewAttached) {
                    view.hideLoading()
                    view.onCompleteMainAction()
                }
             }

            override fun onFailure(e: Exception) {
                if (isViewAttached) {
                    view.hideLoading()
                    view.showMessage("Failure ${e.message}")
                }
            }
        }
    }

}

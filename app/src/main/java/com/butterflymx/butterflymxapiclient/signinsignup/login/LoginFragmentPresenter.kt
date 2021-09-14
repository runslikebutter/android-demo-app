package com.butterflymx.butterflymxapiclient.signinsignup.login

import android.content.Context
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.Constants.SHARED_PREF_KEY_FIREBASE
import com.butterflymx.butterflymxapiclient.utils.mvp.BasePresenter
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.core.Log

class LoginFragmentPresenter : BasePresenter<BaseView>() {

    fun onFailureCallBack(e: Exception) {
        Log.e("AuthorizationListener", "authorization fail with error: ${e.message}")
        if (isViewAttached) {
            view?.hideLoading()
            view?.showMessage("Failure ${e.message}")
        }
    }

    fun onSuccessCallBack() {
        Log.d("AuthorizationListener", "authorization completed")

        view?.hideLoading()

        //register FireBase Token in ButterflyMX SDK
        val mSharedPreferences = App.context?.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val fireBaseToken = mSharedPreferences?.getString(SHARED_PREF_KEY_FIREBASE, "") ?: ""
        if (fireBaseToken.isNotEmpty()) {
            Log.d("AuthorizationListener", "FCM token: $fireBaseToken")
            // register FCM token
            view?.onCompleteMainAction()
        } else {
            App.context?.getString(R.string.empty_fb_token)?.let { view?.showMessage(it) }
        }
    }
}

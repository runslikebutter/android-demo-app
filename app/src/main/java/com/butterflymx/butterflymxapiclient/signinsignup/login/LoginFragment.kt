package com.butterflymx.butterflymxapiclient.signinsignup.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.ButterflyMxConfigBuilder
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.core.AuthorizationListener
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.EndpointType
import com.butterflymx.sdk.core.Log
import kotlinx.android.synthetic.main.login.*
import javax.inject.Inject

class LoginFragment : BaseView() {

    companion object {
        private const val BMX_REQUEST_CODE = 145
    }

    @JvmField
    @Inject
    var presenter: LoginFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.dagger?.injectLoginFragment(this)
        presenter?.attachView(this)

        bt_login?.setOnClickListener { login() }
    }

    fun login() {
        activity?.let {
            showLoading()

            BMXCore.getInstance(it).events.register(object : AuthorizationListener {
                override fun onSuccess() {
                    presenter?.onSuccessCallBack()
                }

                override fun onFailure(e: Exception) {
                    presenter?.onFailureCallBack(e)
                }
            })

            val config = when (rg_server?.checkedRadioButtonId) {
                R.id.rb_staging -> {
                    ButterflyMxConfigBuilder.getButterflyMxConfig(EndpointType.STAGING, it)
                }
                R.id.rb_production -> {
                    ButterflyMxConfigBuilder.getButterflyMxConfig(EndpointType.PROD, it)
                }
                R.id.rb_sandbox -> {
                    ButterflyMxConfigBuilder.getButterflyMxConfig(EndpointType.SANDBOX, it)
                }
                else -> {
                    hideLoading()
                    showMessage(getString(R.string.server_error))
                    null
                }
            }

            if (config != null) {
                BMXCore.getInstance(it).setConfig(config)
                val redirectUri = getString(R.string.redirect_uri)
                val oAuthIntent = BMXCore.getInstance(it).getAuthorizationRequestIntent(redirectUri)
                startActivityForResult(oAuthIntent, BMX_REQUEST_CODE)
            } else {
                hideLoading()
                showMessage(getString(R.string.server_error))
            }

        }
    }

    override fun onCompleteMainAction() {
        activity?.let {
            Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.mainTabFragment)
        }
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("AuthorizationListener", "resultcode: $resultCode")
        if (activity != null && requestCode == BMX_REQUEST_CODE) {
            BMXCore.getInstance(activity!!).processAuthorizationResponse(data!!)
        }
    }
}

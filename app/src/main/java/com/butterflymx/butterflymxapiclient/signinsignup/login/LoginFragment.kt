package com.butterflymx.butterflymxapiclient.signinsignup.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.UiLocker
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import kotlinx.android.synthetic.main.login.*
import javax.inject.Inject

class LoginFragment : BaseView() {

    @Inject
    lateinit var presenter: LoginFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.dagger?.injectLoginFragment(this)
        presenter.attachView(this)

        bt_login.setOnClickListener { login() }
        bt_login.isEnabled = false
        et_login.addTextChangedListener(UiLocker.EmailTextWatcher(bt_login, et_login))
    }

    private fun login() {
        presenter.login(et_login.editableText.toString(), et_password.editableText.toString())
    }


    override fun onCompleteMainAction() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(bt_login.windowToken, 0)
        Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(R.id.mainTabFragment)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}

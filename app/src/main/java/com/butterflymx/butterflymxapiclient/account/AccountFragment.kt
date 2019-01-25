package com.butterflymx.butterflymxapiclient.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.core.ButterflyMxApp
import kotlinx.android.synthetic.main.account.*

class AccountFragment : BaseView() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_sign_out.setOnClickListener {
            ButterflyMxApp.getInstance(App.getContext()).signOut()
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(R.id.mainFragment)
        }
    }

    override fun onCompleteMainAction() {
    }

}
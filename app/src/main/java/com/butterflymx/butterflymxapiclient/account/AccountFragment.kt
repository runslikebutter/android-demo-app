package com.butterflymx.butterflymxapiclient.account

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXCore
import kotlinx.android.synthetic.main.account.*

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_sign_out.setOnClickListener {
            BMXCore.getInstance(App.context).signOut()
            activity?.let{
                Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.mainFragment)
            }
        }

        tv_full_name.text = BMXCore.getInstance(App.context).user.displayName
        et_email.setText(BMXCore.getInstance(App.context).user.email)
        et_phone_number.setText(BMXCore.getInstance(App.context).user.phoneNumber)
    }
}
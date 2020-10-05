package com.butterflymx.butterflymxapiclient.account

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.sdk.core.BMXCore
import kotlinx.android.synthetic.main.account.*

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_sign_out.setOnClickListener {
            activity?.let {
                val sharedPreferences = it.getSharedPreferences(Constants.SHARED_PREF_KEY_ENDPOINT, Context.MODE_PRIVATE)
                sharedPreferences.edit().remove(Constants.SHARED_PREF_KEY_ENDPOINT).apply()
            }

            App.context?.let { it1 -> BMXCore.getInstance(it1).signOut() }
            activity?.let {
                Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.mainFragment)
            }
        }

        App.context?.let {
            tv_full_name.text = BMXCore.getInstance(it).user.displayName
            et_email.setText(BMXCore.getInstance(it).user.email)
            et_phone_number.setText(BMXCore.getInstance(it).user.phoneNumber)
        }
    }
}
package com.butterflymx.butterflymxapiclient

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.sdk.core.ButterflyMxApp

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prepareNextScreen()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun prepareNextScreen() {
        if (ButterflyMxApp.getInstance(App.getContext()).isAuthorized()) {
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(R.id.mainTabFragment)
        } else {
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(R.id.signInUpRootFragment)
        }
    }
}

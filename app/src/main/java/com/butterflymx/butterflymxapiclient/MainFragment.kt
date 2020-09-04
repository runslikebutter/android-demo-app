package com.butterflymx.butterflymxapiclient

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.sdk.core.BMXCore

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prepareNextScreen()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun prepareNextScreen() {
        activity?.let {
            if (App.context?.let { it1 -> BMXCore.getInstance(it1).isAuthorized() } == true) {
                Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.mainTabFragment)
            } else {
                Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.signInUpRootFragment)
            }
        }
    }
}

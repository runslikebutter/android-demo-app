package com.butterflymx.butterflymxapiclient.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import kotlinx.android.synthetic.main.features.*

class FeaturesFragment : BaseView() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.features, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ll_open_door.setOnClickListener { swipeToOpenDoor() }
    }

    private fun swipeToOpenDoor() {
        Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(R.id.select_unit_fragment)
    }

}
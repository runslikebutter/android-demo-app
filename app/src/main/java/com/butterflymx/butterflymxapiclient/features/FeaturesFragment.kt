package com.butterflymx.butterflymxapiclient.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import kotlinx.android.synthetic.main.features.*

class FeaturesFragment : BaseView() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.features, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_to_open_door.setOnClickListener { swipeToOpenDoor() }
        use_a_virtual_key.setOnClickListener { useVirtualKey() }
    }

    private fun useVirtualKey() {

    }

    private fun swipeToOpenDoor() {

    }

    override fun onCompleteMainAction() {

    }
}
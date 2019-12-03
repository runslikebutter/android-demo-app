package com.butterflymx.butterflymxapiclient.features.opendoor

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.interfaces.BMXPanel
import com.butterflymx.sdk.core.interfaces.BMXUnit

class SelectUnitFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).popBackStack() }
        toolbar.title = getString(R.string.select_unit)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)


        recyclerView.adapter = UnitAdapter(BMXCore.getInstance(App.context).user.tenants, activity)

        return view
    }
}

package com.butterflymx.butterflymxapiclient.features.opendoor

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXCore

class SelectUnitFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.let { it1 -> Navigation.findNavController(it1, R.id.my_nav_host_fragment).popBackStack() } }
        toolbar.title = getString(R.string.select_unit)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)


        App.context?.let {
            recyclerView.adapter = UnitAdapter(BMXCore.getInstance(it).user.tenants, activity)
        }

        return view
    }
}

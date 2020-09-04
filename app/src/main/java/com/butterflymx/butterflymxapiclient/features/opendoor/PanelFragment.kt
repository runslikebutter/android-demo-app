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

class PanelFragment : Fragment() {

    companion object {
        val CHOSEN_UNIT = "chosen_unit"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.let { it1 -> Navigation.findNavController(it1, R.id.my_nav_host_fragment).popBackStack() } }
        toolbar.title = getString(R.string.select_panel)

        if (arguments != null) {
            val unitId = (arguments as Bundle).getLong(CHOSEN_UNIT)
            val panelList = filterPanelsByUnitId(unitId)
            if (panelList.isEmpty()) {
                activity?.let { Navigation.findNavController(it, R.id.my_nav_host_fragment).popBackStack() }
            } else {
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.adapter = PanelAdapter(panelList, activity)
            }
        }

        return view
    }

    private fun filterPanelsByUnitId(unitId: Long?): List<BMXPanel> {
        val panelList = ArrayList<BMXPanel>()
        val user = App.context?.let { BMXCore.getInstance(it).user }
        if (user != null) {
            for (tenant in user.tenants) {
                if (tenant.unit?.id == unitId) {
                    for (panel in tenant.panels) {
                        panelList.add(panel)
                    }
                }
            }
        }
        return panelList
    }
}
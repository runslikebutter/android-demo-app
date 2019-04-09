package com.butterflymx.butterflymxapiclient.features.opendoor

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.RequestCallBack
import com.butterflymx.sdk.core.interfaces.BMXPanel

class SelectPanelFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).popBackStack() }
        toolbar.title = getString(R.string.select_panel)

        val unitId = arguments?.getLong(UnitsAdapter.CHOSEN_UNIT)

        val panelList = BMXCore.getInstance(App.getContext()).user.units.find { it.id == unitId }?.panels

        if (panelList == null) {
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).popBackStack()
        } else {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView.adapter = PanelAdapter(panelList, activity)
        }
        return view
    }

}

class PanelAdapter(val panelList: List<BMXPanel>, val activity: Activity?) : RecyclerView.Adapter<SimpleListFragmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SimpleListFragmentVH {
        return SimpleListFragmentVH(LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return panelList.size
    }

    override fun onBindViewHolder(viewHolder: SimpleListFragmentVH, position: Int) {
        viewHolder.title.text = panelList[position].name
        viewHolder.subTitle.text = "Id: ${panelList[position].id}"

        viewHolder.root.setOnClickListener {
            if (activity != null) {
                panelList[position].openDoor(object : RequestCallBack {
                    override fun onFailure(e: Exception) {
                        Toast.makeText(activity, "${panelList[position].name} can't be opened", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess() {
                        Toast.makeText(activity, "${panelList[position].name} opened", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

}
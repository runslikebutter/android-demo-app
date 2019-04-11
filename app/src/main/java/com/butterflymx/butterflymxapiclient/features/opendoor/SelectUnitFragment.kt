package com.butterflymx.butterflymxapiclient.features.opendoor

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.interfaces.BMXUnit

class SelectUnitFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).popBackStack() }
        toolbar.title = getString(R.string.select_unit)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = UnitsAdapter(BMXCore.getInstance(App.getContext()).user.units, activity)

        return view
    }
}

class UnitsAdapter(val unitList: List<BMXUnit>, val activity: Activity?) : RecyclerView.Adapter<SimpleListFragmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SimpleListFragmentVH {
        return SimpleListFragmentVH(LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return unitList.size
    }

    override fun onBindViewHolder(viewHolder: SimpleListFragmentVH, position: Int) {
        viewHolder.title.text = unitList[position].name.substring(0, 1).toUpperCase() + unitList[position].name.substring(1)

        if (unitList[position].isOpenDoorEnabled) {
            viewHolder.subTitle.text = activity?.getString(R.string.select_unit_fragment_available)
            if (activity != null) {
                viewHolder.subTitle.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.available_unit_subtitle))
            }
            viewHolder.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putLong(CHOSEN_UNIT, unitList[position].id)
                if (activity != null) {
                    Navigation.findNavController(activity, R.id.my_nav_host_fragment).navigate(R.id.select_panel_fragment, bundle)
                }
            }
        } else {
            viewHolder.subTitle.text = activity?.getString(R.string.select_unit_fragment__not_available)
            if (activity != null) {
                viewHolder.subTitle.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.not_available_unit_subtitle))
                viewHolder.title.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.not_available_unit_title))
            }
        }
    }


    companion object {
        val CHOSEN_UNIT = "chosen_unit"
    }
}
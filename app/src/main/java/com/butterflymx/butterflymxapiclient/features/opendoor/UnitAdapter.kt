package com.butterflymx.butterflymxapiclient.features.opendoor

import android.app.Activity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.features.opendoor.PanelFragment.Companion.CHOSEN_UNIT
import com.butterflymx.sdk.core.interfaces.BMXTenant


class UnitAdapter(private val tenantList: List<BMXTenant>,
                  val activity: Activity?) : RecyclerView.Adapter<SimpleListFragmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SimpleListFragmentVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false)
        return SimpleListFragmentVH(view)
    }

    override fun getItemCount(): Int {
        return tenantList.size
    }

    override fun onBindViewHolder(viewHolder: SimpleListFragmentVH, position: Int) {
        val unit = tenantList[position].unit ?: return
        val title = (unit.name.substring(0, 1).toUpperCase() + unit.name.substring(1))

        viewHolder.title.text = title

        if (tenantList[position].isOpenDoorEnabled) {
            viewHolder.subTitle.text = activity?.getString(R.string.select_unit_fragment_available)
            if (activity != null) {
                viewHolder.subTitle.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.available_unit_subtitle))
            }
            viewHolder.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putLong(CHOSEN_UNIT, tenantList[position].unit?.id ?: 0)
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
}
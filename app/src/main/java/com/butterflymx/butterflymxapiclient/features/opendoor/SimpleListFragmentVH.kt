package com.butterflymx.butterflymxapiclient.features.opendoor

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.simple_list_item.view.*

class SimpleListFragmentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val root = itemView.rootView
    val title = itemView.tv_title
    val subTitle = itemView.tv_sub_title
}

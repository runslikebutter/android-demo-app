package com.butterflymx.butterflymxapiclient.features.opendoor

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.simple_list_item.view.*

class SimpleListFragmentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val root: View = itemView.rootView
    val title: TextView = itemView.tv_title
    val subTitle: TextView = itemView.tv_sub_title
    val progressBar: ProgressBar = itemView.progress_bar
}

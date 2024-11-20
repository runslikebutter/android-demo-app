package com.butterflymx.butterflymxapiclient.features.opendoor

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.butterflymx.butterflymxapiclient.R

class SimpleListFragmentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val root: View = itemView.rootView
    val title: TextView = itemView.findViewById(R.id.tv_title)
    val subTitle: TextView = itemView.findViewById(R.id.tv_sub_title)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
}

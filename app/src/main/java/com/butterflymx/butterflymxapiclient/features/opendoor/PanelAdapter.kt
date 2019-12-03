package com.butterflymx.butterflymxapiclient.features.opendoor

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.RequestCallBack
import com.butterflymx.sdk.core.interfaces.BMXPanel

class PanelAdapter(val panelList: List<BMXPanel>, val activity: Activity?) : RecyclerView.Adapter<SimpleListFragmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SimpleListFragmentVH {
        return SimpleListFragmentVH(LayoutInflater.from(parent.context).inflate(R.layout.simple_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return panelList.size
    }

    override fun onBindViewHolder(viewHolder: SimpleListFragmentVH, position: Int) {
        viewHolder.title.text = panelList[position].name
        viewHolder.subTitle.text = activity?.getString(R.string.select_panel_fragment_press_to_open)
        if (activity != null) {
            viewHolder.subTitle.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.bmx_blue))
        }
        viewHolder.root.setOnClickListener {
            if (activity != null) {
                viewHolder.subTitle.text = activity.getString(R.string.select_panel_fragment_wait)
                viewHolder.subTitle.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.gray))
                viewHolder.progressBar.visibility = View.VISIBLE

                viewHolder.root.isEnabled = false
                panelList[position].openDoor(object : RequestCallBack {
                    override fun onFailure(e: Exception) {
                        viewHolder.progressBar.visibility = View.GONE
                        showMessageInSubTitle("${panelList[position].name} can't be opened", viewHolder.subTitle, viewHolder.title, viewHolder.root)
                    }

                    override fun onSuccess() {
                        viewHolder.progressBar.visibility = View.GONE
                        showMessageInSubTitle("${panelList[position].name} opened", viewHolder.subTitle, viewHolder.title, viewHolder.root)
                    }

                })
            }
        }
    }

    private fun showMessageInSubTitle(message: String, subTitleTextView: TextView, titleTextView: TextView, root: View) {
        subTitleTextView.text = message
        if (activity != null) {
            subTitleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.gray))
            titleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.gray))
        }
        val h = Handler(Looper.getMainLooper())

        h.postDelayed({
            subTitleTextView.text = activity?.getString(R.string.select_panel_fragment_press_to_open)
            if (activity != null) {
                subTitleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.bmx_blue))
                titleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.black))
            }
            root.isEnabled = true
        }, 5000)
    }

}
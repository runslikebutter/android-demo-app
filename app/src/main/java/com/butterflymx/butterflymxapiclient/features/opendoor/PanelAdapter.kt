package com.butterflymx.butterflymxapiclient.features.opendoor

import android.app.Activity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.databinding.SimpleListItemBinding
import com.butterflymx.sdk.core.RequestCallBack
import com.butterflymx.sdk.core.interfaces.BMXPanel

class PanelAdapter(val panelList: List<BMXPanel>, val activity: Activity?) : RecyclerView.Adapter<SimpleListFragmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SimpleListFragmentVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SimpleListItemBinding.inflate(inflater, parent, false)
        return SimpleListFragmentVH(binding)
    }

    override fun getItemCount(): Int {
        return panelList.size
    }

    override fun onBindViewHolder(holder: SimpleListFragmentVH, position: Int) {
        // Bind the *current* item once
        val item = panelList[position]

        holder.title.text = "${item.id} - ${item.name}"
        holder.subTitle.text = holder.itemView.context.getString(R.string.select_panel_fragment_press_to_open)
        holder.subTitle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.bmx_blue))
        holder.progressBar.visibility = View.GONE
        holder.root.isEnabled = true

        holder.root.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener

            val current = panelList[pos]

            holder.subTitle.text = holder.itemView.context.getString(R.string.select_panel_fragment_wait)
            holder.subTitle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
            holder.progressBar.visibility = View.VISIBLE
            holder.root.isEnabled = false

            current.openDoor(object : RequestCallBack {
                override fun onFailure(e: Exception) {
                    val p = holder.adapterPosition
                    if (p == RecyclerView.NO_POSITION || panelList.getOrNull(p) !== current) return

                    holder.progressBar.visibility = View.GONE
                    showMessageInSubTitle("${current.name} can't be opened", holder.subTitle, holder.title, holder.root)
                    holder.root.isEnabled = true
                }

                override fun onSuccess() {
                    val p = holder.adapterPosition
                    if (p == RecyclerView.NO_POSITION || panelList.getOrNull(p) !== current) return

                    holder.progressBar.visibility = View.GONE
                    showMessageInSubTitle("${current.name} opened", holder.subTitle, holder.title, holder.root)
                    holder.root.isEnabled = true
                }
            })
        }
    }

    private fun showMessageInSubTitle(message: String, subTitleTextView: TextView, titleTextView: TextView, root: View) {
        subTitleTextView.text = message
        if (activity != null) {
            subTitleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.gray))
            titleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.gray))
        }

        subTitleTextView.postDelayed({
            subTitleTextView.text = activity?.getString(R.string.select_panel_fragment_press_to_open)
            if (activity != null) {
                subTitleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.bmx_blue))
                titleTextView.setTextColor(ContextCompat.getColor(activity.applicationContext, R.color.black))
            }
            root.isEnabled = true
        }, 5000)
    }

}
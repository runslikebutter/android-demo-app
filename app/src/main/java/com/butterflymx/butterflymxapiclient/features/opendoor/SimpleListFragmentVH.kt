package com.butterflymx.butterflymxapiclient.features.opendoor

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.butterflymx.butterflymxapiclient.databinding.SimpleListItemBinding

class SimpleListFragmentVH(
    val binding: SimpleListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    val root: View get() = binding.root
    val title: TextView get() = binding.tvTitle
    val subTitle: TextView get() = binding.tvSubTitle
    val progressBar: ProgressBar get() = binding.progressBar
}

package com.butterflymx.butterflymxapiclient.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.databinding.FeaturesBinding
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView

class FeaturesFragment : BaseView() {

    private var _binding: FeaturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FeaturesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llOpenDoor.setOnClickListener { swipeToOpenDoor() }
    }

    private fun swipeToOpenDoor() {
        activity?.let { Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.select_unit_fragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
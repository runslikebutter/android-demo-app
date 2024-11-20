package com.butterflymx.butterflymxapiclient.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.databinding.FeaturesBinding
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.core.BMXCore
import com.butterflymx.sdk.core.Log

class FeaturesFragment : BaseView() {

    private var _binding: FeaturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FeaturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llOpenDoor.setOnClickListener { swipeToOpenDoor() }

        binding.btnRefreshToken.setOnClickListener {
            App.context?.let { it1 ->
                BMXCore.getInstance(it1).refreshToken { authTokens ->
                    if (authTokens == null) {
                        Log.i("AuthTokens", "authTokens is null")
                    } else {
                        Log.i("AuthTokens", "Access Token: ${authTokens.accessToken} ${authTokens.refreshToken}")
                    }
                }
            }
        }
    }

    private fun swipeToOpenDoor() {
        activity?.let { Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.select_unit_fragment) }
    }

}
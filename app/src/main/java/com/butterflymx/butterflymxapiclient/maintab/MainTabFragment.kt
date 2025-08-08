package com.butterflymx.butterflymxapiclient.maintab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.account.AccountFragment
import com.butterflymx.butterflymxapiclient.databinding.MainTabBinding
import com.butterflymx.butterflymxapiclient.features.FeaturesFragment
import com.butterflymx.sdk.core.Log

class MainTabFragment : Fragment() {

    companion object {
        const val TAG = "MainTabFragment"
        private const val ACCESS_CAMERA_N_MIC_CODE_REQUEST = 200
    }

    private var _binding: MainTabBinding? = null
    private val binding get() = _binding!!

    private val requestMultiplePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            Log.i(TAG, "All Permissions have been granted")
        } else {
            Log.i(TAG, "Permission(s) denied")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = MainTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBottomMenu()
        askPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionState = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS)
            if (permissionState == PackageManager.PERMISSION_DENIED) {
                requestMultiplePermissionsLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    "android.permission.POST_NOTIFICATIONS"
                    )
                )
            }
        } else {
            if ((activity?.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        activity?.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), ACCESS_CAMERA_N_MIC_CODE_REQUEST)
            }
        }
    }

    private fun initView() {
        binding.viewPager.adapter =
            PagerAdapter(childFragmentManager, listOf(FeaturesFragment(), AccountFragment()))
    }

    private fun initBottomMenu() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            with(binding.viewPager) {
                when (menuItem.itemId) {
                    R.id.action_features -> currentItem = 0
                    R.id.action_account -> currentItem = 1
                    else -> currentItem = 0
                }
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                activity?.finish()
                return@OnKeyListener true
            }
            false
        })
    }
}

class PagerAdapter(fm: FragmentManager, private val fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}
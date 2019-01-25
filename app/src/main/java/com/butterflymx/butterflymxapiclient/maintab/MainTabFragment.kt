package com.butterflymx.butterflymxapiclient.maintab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.butterflymx.butterflymxapiclient.account.AccountFragment
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.features.FeaturesFragment
import kotlinx.android.synthetic.main.main_tab.*

class MainTabFragment : Fragment() {

    private val ACCESS_CAMERA_N_MIC_CODE_REQUEST = 200

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBottomMenu()
        askPermissions()
    }

    private fun askPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 &&
                (activity?.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        activity?.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO), ACCESS_CAMERA_N_MIC_CODE_REQUEST)
        }
    }

    private fun initView() {
        view_pager.adapter = PagerAdapter(childFragmentManager, listOf(FeaturesFragment(), AccountFragment()))
    }

    private fun initBottomMenu() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_features -> view_pager.currentItem = 0
                R.id.action_account -> view_pager.currentItem = 1
                else -> view_pager.currentItem = 0
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
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
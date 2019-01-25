package com.butterflymx.butterflymxapiclient.utils.mvp

import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.butterflymx.butterflymxapiclient.R

abstract class BaseView : Fragment(), MvpView {

    lateinit var dialog: AlertDialog

    override fun showLoading() {
        if (activity == null) return
        val builder = AlertDialog.Builder(activity!!)
        builder.setView(R.layout.progress)
        dialog = builder.create()
        dialog.show()
    }

    override fun hideLoading() {
        if (activity == null) return
        dialog.dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}
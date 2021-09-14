package com.butterflymx.butterflymxapiclient.utils.mvp

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import com.butterflymx.butterflymxapiclient.R

abstract class BaseView : Fragment(), MvpView {

    private lateinit var dialog: AlertDialog

    override fun showLoading() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(R.layout.progress)
            dialog = builder.create()
            dialog.show()
        }
    }

    override fun hideLoading() {
        if (activity == null) return
        dialog.dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCompleteMainAction() {}

}
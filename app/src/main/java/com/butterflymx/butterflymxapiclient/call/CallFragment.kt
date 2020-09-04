package com.butterflymx.butterflymxapiclient.call

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.call.BMXCall
import com.butterflymx.sdk.call.CallState
import com.butterflymx.sdk.call.CallStateListener
import com.butterflymx.sdk.call.interfaces.Call
import kotlinx.android.synthetic.main.incoming_call.*


class CallFragment : BaseView() {

    private lateinit var mCall: Call
    private lateinit var callStatus: CallState

    private var isCameraChecked = false
    private var isSpeakerChecked = true
    private var isMicChecked = false

    private var panelName: String? = ""
    private var guid: String? = ""
    private var TAG = "Call status"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.incoming_call, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            BMXCall.getInstance(it).events.register(object : CallStateListener {
                override fun onCallState(state: CallState, call: Call) {
                    when (state) {
                        CallState.END -> it.finish()
                    }
                }
            })
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = arguments
        panelName = intent?.getString(Constants.CALL_PANEL_NAME)
        guid = intent?.getString(Constants.CALL_GUID)

        App.context?.let {
            mCall = BMXCall.getInstance(it).getCall(guid.toString())
        }
        initView()
        initCall()
    }

    private fun initView() {
        group_init_call.visibility = View.VISIBLE
        group_control_call.visibility = View.GONE

        val callAnimation = AnimationUtils.loadAnimation(activity, R.anim.call_animation)
        fab_accept.startAnimation(callAnimation)
        fab_decline_new_call.startAnimation(callAnimation)

        tv_panel_name.text = panelName

        fab_open_door.setOnClickListener {
            openDoor()
        }

        tv_door.setOnClickListener {
            openDoor()
        }

        fab_decline.setOnClickListener {
            finishCall()
        }

        fab_decline_new_call.setOnClickListener {
            finishCall()
        }

        iv_speaker.setOnClickListener {
            isSpeakerChecked = !isSpeakerChecked
            mCall.enableSpeaker(isSpeakerChecked)

            if (isSpeakerChecked) {
                iv_speaker.setImageResource(R.drawable.button_speaker_active)
            } else {
                iv_speaker.setImageResource(R.drawable.button_speaker)
            }
        }

        iv_camera_switch.setOnClickListener {
            cameraListener()
        }

        iv_mic_switch.setOnClickListener {
            micListener()
        }

        fab_accept.setOnClickListener {
            acceptCall()
        }
    }

    private fun acceptCall() {
        fab_accept.clearAnimation()
        fab_decline_new_call.clearAnimation()
        group_init_call.visibility = View.GONE
        group_control_call.visibility = View.VISIBLE
        mCall.answer()
    }

    private fun micListener() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 &&
                activity?.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            showMessage("You should give permission")
        } else {
            isMicChecked = !isMicChecked
            mCall.muteMic(isMicChecked)
            if (isMicChecked) {
                iv_mic_switch.setImageResource(R.drawable.button_mic_active)
            } else {
                iv_mic_switch.setImageResource(R.drawable.button_mic)
            }
        }
    }

    private fun cameraListener() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 &&
                activity?.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            showMessage("You should give camera permission")
        } else {
            isCameraChecked = !isCameraChecked
            mCall.enableCamera(isCameraChecked)

            if (isCameraChecked) {
                iv_camera_switch.setImageResource(R.drawable.button_camera_active)

            } else {
                iv_camera_switch.setImageResource(R.drawable.button_camera)

            }
        }
    }

    private fun initCall() {
        mCall.preview(video_surface_incoming, video_surface_outgoing)
    }

    private fun finishCall() {
        mCall.hangUp()
        activity?.finish()
    }

    private fun openDoor() {
        mCall.openDoor()
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroyView() {
        mCall.hangUp()
        super.onDestroyView()
    }

}
package com.butterflymx.butterflymxapiclient.call

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.databinding.IncomingCallBinding
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.butterflymxapiclient.utils.mvp.BaseView
import com.butterflymx.sdk.call.BMXCall
import com.butterflymx.sdk.call.CallState
import com.butterflymx.sdk.call.CallStateListener
import com.butterflymx.sdk.call.interfaces.Call
import java.util.*


class CallFragment : BaseView() {

    private var _binding: IncomingCallBinding? = null
    private val binding get() = _binding!!

    private lateinit var mCall: Call
    private lateinit var callStatus: CallState

    private var isCameraChecked = false
    private var isSpeakerChecked = true
    private var isMicChecked = false
    private var fullScreenVideo = false

    private var panelName: String? = ""
    private var guid: String? = ""
    private var TAG = "Call status"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = IncomingCallBinding.inflate(inflater, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            BMXCall.getInstance(it).events.register(object : CallStateListener {
                override fun onCallState(state: CallState, call: Call) {
                    if (state == CallState.END) it.finish()
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
        binding.groupInitCall.visibility = View.VISIBLE
        binding.groupControlCall.visibility = View.GONE

        val callAnimation = AnimationUtils.loadAnimation(activity, R.anim.call_animation)
        binding.fabAccept.startAnimation(callAnimation)
        binding.fabDeclineNewCall.startAnimation(callAnimation)

        binding.tvPanelName.text = panelName

        binding.buttonScreenToggle.setOnClickListener {
            fullScreenVideo = !fullScreenVideo
            setFullscreenVideo(fullScreenVideo)
        }

        binding.fabOpenDoor.setOnClickListener {
            openDoor()
        }

        binding.tvDoor.setOnClickListener {
            openDoor()
        }

        binding.fabDecline.setOnClickListener {
            finishCall()
        }

        binding.fabDeclineNewCall.setOnClickListener {
            finishCall()
        }

        binding.ivSpeaker.setOnClickListener {
            isSpeakerChecked = !isSpeakerChecked
            mCall.enableSpeaker(isSpeakerChecked)

            if (isSpeakerChecked) {
                binding.ivSpeaker.setImageResource(R.drawable.button_speaker_active)
            } else {
                binding.ivSpeaker.setImageResource(R.drawable.button_speaker)
            }
        }

        binding.ivCameraSwitch.setOnClickListener {
            cameraListener()
        }

        binding.ivMicSwitch.setOnClickListener {
            micListener()
        }

        binding.fabAccept.setOnClickListener {
            acceptCall()
        }
    }

    private fun acceptCall() {
        binding.fabAccept.clearAnimation()
        binding.fabDeclineNewCall.clearAnimation()
        binding.groupInitCall.visibility = View.GONE
        binding.groupControlCall.visibility = View.VISIBLE
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
                binding.ivMicSwitch.setImageResource(R.drawable.button_mic_active)
            } else {
                binding.ivMicSwitch.setImageResource(R.drawable.button_mic)
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
                binding.ivCameraSwitch.setImageResource(R.drawable.button_camera_active)

            } else {
                binding.ivCameraSwitch.setImageResource(R.drawable.button_camera)

            }
        }
    }

    private fun initCall() {
        mCall.preview(binding.videoSurfaceIncoming, binding.videoSurfaceOutgoing)
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

    private fun setFullscreenVideo(fullScreen: Boolean) {
        //fix videoView height for different screens
        //get screen width
        val screenWidth: Int
        val screenHeight: Int
        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y

        if (screenWidth > 0) {
            // 1.333 this is the proportion between width/height
            val layoutParams = binding.videoSurfaceIncoming?.layoutParams as ConstraintLayout.LayoutParams

            if (fullScreen) {
                layoutParams.height = screenHeight
                val finalWidth = screenHeight * 4 / 3
                val padding = (screenWidth - finalWidth) / 2
                layoutParams.setMargins(padding, 0, padding, 0)
                binding.videoSurfaceIncoming?.layoutParams = layoutParams

                Log.i(TAG + "/initVideoViews: ", String.format(
                    Locale.US, "set video view height to: %d, width: %d, padding: %d, fullScreen: %b",
                    layoutParams.height, screenWidth, padding, fullScreen))
                fixZOrder()
            } else {
                //			set video view height
                layoutParams.height = screenWidth * 3 / 4
                layoutParams.setMargins(0, 0, 0, 0)
                binding.videoSurfaceIncoming?.layoutParams = layoutParams

                Log.i(TAG + "/initVideoViews: ", String.format(
                    Locale.US, "set video view height to: %d, width: %d, fullScreen: %b",
                    binding.videoSurfaceIncoming?.layoutParams?.height ?: 0, screenWidth, fullScreen))
            }
        }
    }

    private fun fixZOrder() {
        Log.i(TAG,"fixZOrder()")
        binding.videoSurfaceIncoming?.setZOrderOnTop(false)
        binding.videoSurfaceOutgoing?.setZOrderOnTop(true)
        binding.videoSurfaceOutgoing?.setZOrderMediaOverlay(true)
    }

}
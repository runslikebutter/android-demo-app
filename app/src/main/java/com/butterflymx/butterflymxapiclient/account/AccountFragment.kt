package com.butterflymx.butterflymxapiclient.account

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.butterflymx.butterflymxapiclient.App
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.butterflymxapiclient.databinding.AccountBinding
import com.butterflymx.butterflymxapiclient.utils.Constants
import com.butterflymx.sdk.core.BMXCore

class AccountFragment : Fragment() {

    private var _binding: AccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = AccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSignOut.setOnClickListener {
            activity?.let {
                val sharedPreferences = it.getSharedPreferences(Constants.SHARED_PREF_KEY_ENDPOINT, Context.MODE_PRIVATE)
                sharedPreferences.edit().remove(Constants.SHARED_PREF_KEY_ENDPOINT).apply()
            }

            App.context?.let { it1 -> BMXCore.getInstance(it1).signOut() }
            activity?.let {
                Navigation.findNavController(it, R.id.my_nav_host_fragment).navigate(R.id.mainFragment)
            }
        }

        App.context?.let {
            binding.tvFullName.text = BMXCore.getInstance(it).user.displayName
            binding.etEmail.setText(BMXCore.getInstance(it).user.email)
            binding.etPhoneNumber.setText(BMXCore.getInstance(it).user.phoneNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
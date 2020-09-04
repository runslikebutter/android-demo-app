package com.butterflymx.butterflymxapiclient.utils

import android.content.Context
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXConfig
import com.butterflymx.sdk.core.EndpointType

object ButterflyMxConfigBuilder {

    fun getButterflyMxConfig(endpointType: EndpointType, context: Context): BMXConfig {
        var clientId: String? = null
        var secretId: String? = null

        when (endpointType) {
            EndpointType.STAGING -> {
                clientId = context.getString(R.string.staging_client_id)
                secretId = context.getString(R.string.staging_secret_id)
            }
            EndpointType.PROD -> {
                clientId = context.getString(R.string.prod_client_id)
                secretId = context.getString(R.string.prod_secret_id)
            }
            EndpointType.SANDBOX -> {
                clientId = context.getString(R.string.sandbox_client_id)
                secretId = context.getString(R.string.sandbox_secret_id)
            }
        }

        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(Constants.SHARED_PREF_KEY_ENDPOINT, endpointType.ordinal)?.apply()


        return BMXConfig.Builder()
                .setAuthConfig(clientId, secretId)
                .enableDefaultLogger(false)
                .setEndpointType(endpointType)
                .setLogger(ButterflyMxLogger())
                .build()
    }
}
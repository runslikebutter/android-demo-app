package com.butterflymx.butterflymxapiclient.utils

import android.content.Context
import com.butterflymx.butterflymxapiclient.R
import com.butterflymx.sdk.core.BMXConfig
import com.butterflymx.sdk.core.EndpointType

object ButterflyMxConfigBuilder {

    // Set to false to use the legacy client-secret flow instead
    private const val USE_PKCE_FLOW = true

    fun getButterflyMxConfig(endpointType: EndpointType, context: Context): BMXConfig {
        val clientId: String
        val secretId: String?

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
        sharedPreferences?.edit()?.putInt(Constants.SHARED_PREF_KEY_ENDPOINT, endpointType.ordinal)?.apply()

        val builder = BMXConfig.Builder()
            .enableDefaultLogger(false)
            .setEndpointType(endpointType)
            .setLogger(ButterflyMxLogger())

        return if (USE_PKCE_FLOW) {
            // PKCE-only flow (recommended) — no secret needed
            builder.setClientID(clientId).build()
        } else {
            // Legacy client-secret flow — set USE_PKCE_FLOW = false to use this path
            builder.setAuthConfig(clientId, secretId).build()
        }
    }
}

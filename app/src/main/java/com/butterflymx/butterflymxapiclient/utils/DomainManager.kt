package com.butterflymx.butterflymxapiclient.utils

import android.text.TextUtils
import java.util.regex.Pattern


class DomainManager {


    private val TEST_LOGIN_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}).*/test.*")

    private val HTTPS = "https://"
    private val DOMAIN_ROOT = "butterflymx.com"
    private val FORGOT_PASSWORD_LINK = HTTPS + "user." + DOMAIN_ROOT + "/users/forgot_password"
    private val FORGOT_PASSWORD_LINK_TEST_ACCOUNT = HTTPS + "usertest." + DOMAIN_ROOT + "/users/forgot_password"


    fun getForgotPasswordLink(login: String): String {
        val res = if (validTestAccount(login)) FORGOT_PASSWORD_LINK_TEST_ACCOUNT else FORGOT_PASSWORD_LINK
        return res
    }

    private fun validTestAccount(login: String): Boolean {
        if (TextUtils.isEmpty(login)) return false
        val matcher = TEST_LOGIN_PATTERN.matcher(login)
        return matcher.find()
    }

}

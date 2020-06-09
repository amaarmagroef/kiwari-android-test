package com.chataja.test.utils

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by siapaSAYA on 6/9/2020
 */

class PrefManager(context : Context) {

    private val mPreference : SharedPreferences
    private var mUsername : String?
    private var mEmail : String?
    private var mAvatar : String?

    init {
        mPreference = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        mUsername = mPreference.getString(KEY_USERNAME, null)
        mEmail = mPreference.getString(KEY_EMAIL,null)
        mAvatar = mPreference.getString(KEY_AVATAR,null)
    }

    fun setUsername(username : String){
        mUsername = username
        mPreference.edit().putString(KEY_USERNAME, mUsername).apply()
    }

    fun setEmail(email : String){
        mEmail = email
        mPreference.edit().putString(KEY_EMAIL, mEmail).apply()
    }

    fun setAvatar(avatar : String){
        mAvatar = avatar
        mPreference.edit().putString(KEY_AVATAR, mAvatar).apply()
    }

    fun getUsername() : String{
        return mUsername!!
    }

    fun getEmail() : String{
        return mEmail!!
    }

    companion object {
        const val NAME = "app_user_file"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_AVATAR = "avatar"
    }
}
package com.chataja.test.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chataja.test.features.login.model.UserFirebase
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext


/**
 * Created by siapaSAYA on 6/9/2020
 */

class LoginViewModel: ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    val _users = MutableLiveData<MutableList<UserFirebase?>>()
    val users: LiveData<MutableList<UserFirebase?>>
        get() = _users

    private var tempListUser : MutableList<UserFirebase?> = mutableListOf()

    private var _errorCode = 200
    val errorCode = _errorCode

    private lateinit var mDatabase: DatabaseReference

    init {
        checkData()
    }

    fun checkData(){
        launch {

            /**
             *  already add in database realtime
             */
            createUsers(
                mutableListOf(UserFirebase("Jarjit Singh",
                    "https://api.adorable.io/avatars/160/jarjit@mail.com.png",
                    "jarjit@mail.com",
                    "123456"),

                    UserFirebase(
                        "Ismail bin Mail",
                        "https://api.adorable.io/avatars/160/ismail@mail.com.png",
                        "ismail@mail.com",
                        "123456"))
            )
        }
    }


    fun validEmail(email: String): Boolean {
        val regex = "^[\\w{|}~^-]+(?:\\.[\\w{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
        val emailPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
        val matcher = emailPattern.matcher(email)
        return matcher.matches()
    }

    fun createUsers(list : MutableList<UserFirebase>){
        listUsers = list
    }

    fun isEmptyString(string : String) : Boolean {
        return string.isEmpty()
    }

    fun validPassword(password : String) : Boolean{
        return password.length > 5
    }

    companion object{
        @JvmStatic
        var listUsers = mutableListOf<UserFirebase>()
    }

}
package com.chataja.test.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chataja.test.features.main.model.ChatModelData
import com.chataja.test.features.login.model.UserFirebase
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by siapaSAYA on 6/9/2020
 */


class MainMenuViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private val _chats = MutableLiveData<MutableList<ChatModelData>>()
    val chats: LiveData<MutableList<ChatModelData>>
        get() = _chats

    private val _users = MutableLiveData<MutableList<UserFirebase>>()
    val users: LiveData<MutableList<UserFirebase>>
        get() = _users

    private var mDatabaseUser: DatabaseReference = FirebaseDatabase.getInstance().getReference("user")
    private var mDatabaseChat: DatabaseReference = FirebaseDatabase.getInstance().getReference("chat")

    init {
        launch {
            mDatabaseUser.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val data = mutableListOf<UserFirebase>()
                    for (item in p0.children){
                        val tempModelClass: UserFirebase? =
                            item.getValue(UserFirebase::class.java)
                        data.add(tempModelClass!!)
                    }
                    _users.value = data
                }

            })

            mDatabaseChat.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val data = mutableListOf<ChatModelData>()
                    for (item in p0.children){
                        val tempModelClass: ChatModelData? =
                            item.getValue(ChatModelData::class.java)
                        data.add(tempModelClass!!)
                    }
                    _chats.value = data
                }
            })
        }
    }

    fun pushChat(message : String, username : String){

        mDatabaseChat.push().setValue(
            ChatModelData(
                message, getCurrentDate()!!, username
            )
        )
    }

    fun getCurrentDate(): String? {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy (hh:mm:ss) ", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        val today = Calendar.getInstance().time
        return dateFormat.format(today)
    }
}
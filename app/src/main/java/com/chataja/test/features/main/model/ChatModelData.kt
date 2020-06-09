package com.chataja.test.features.main.model


/**
 * Created by siapaSAYA on 6/9/2020
 */


class ChatModelData() {

    var chat : String? = null
    var time : String? = null
    var username : String? = null

    constructor(_chat: String, _time: String, _username : String) : this() {
        this.chat = _chat
        this.time = _time
        this.username = _username
    }

}

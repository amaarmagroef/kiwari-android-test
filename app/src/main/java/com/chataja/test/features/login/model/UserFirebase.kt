package com.chataja.test.features.login.model
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by siapaSAYA on 6/9/2020
 */

@IgnoreExtraProperties
data class UserFirebase(
    var nama: String? = "",
    var avatar: String? = "",
    var email: String? = "",
    var password: String? = ""
)

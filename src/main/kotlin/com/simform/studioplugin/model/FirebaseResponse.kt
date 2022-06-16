package com.simform.studioplugin.model

import com.google.gson.annotations.SerializedName

class FirebaseResponse {
    @SerializedName("success")
    val success: Int = 0

    @SerializedName("failure")
    val failure: Int = 0
}
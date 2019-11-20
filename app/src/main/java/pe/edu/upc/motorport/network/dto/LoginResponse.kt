package pe.edu.upc.motorport.network.dto

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("token")
    var token: String? = null
}
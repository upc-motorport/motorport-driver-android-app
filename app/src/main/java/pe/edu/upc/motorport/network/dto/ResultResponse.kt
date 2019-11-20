package pe.edu.upc.motorport.network.dto

import com.google.gson.annotations.SerializedName

class ResultResponse<T> {
    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("result")
    var result: T? = null
}
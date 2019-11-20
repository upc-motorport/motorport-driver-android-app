package pe.edu.upc.motorport.network.dto

import com.google.gson.annotations.SerializedName

data class SaveVehicleRequest(
    @SerializedName("registrationPlate") val registrationPlate: String,
    @SerializedName("subscriptionId") val subscriptionId: Int,
    @SerializedName("model") val model: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("type") val type: String,
    @SerializedName("year") val year: Int,
    @SerializedName("kilometers") val kilometers: Int
)
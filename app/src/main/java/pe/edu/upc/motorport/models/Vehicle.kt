package pe.edu.upc.motorport.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Vehicle: Serializable {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("registrationPlate")
    var registrationPlate: String? = null
    @SerializedName("type")
    var type: String? = null
    @SerializedName("model")
    var model: String? = null
    @SerializedName("brand")
    var brand: String? = null
    @SerializedName("year")
    var year: Int? = null
    @SerializedName("kilometers")
    var kilometers: Int? = null
    @SerializedName("imageUrl")
    var imageUrl: String? = null
}
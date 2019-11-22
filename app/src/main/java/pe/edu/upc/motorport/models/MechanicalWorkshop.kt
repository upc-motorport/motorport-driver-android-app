package pe.edu.upc.motorport.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "mechanical_workshops")
class MechanicalWorkshop: Serializable {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @ColumnInfo(name="name")
    @SerializedName("name")
    var name: String? = null

    @ColumnInfo(name="image_url")
    @SerializedName("imageUrl")
    var imageUrl: String? = null

    @ColumnInfo(name="average_rate")
    @SerializedName("averageRate")
    var averageRate: Double? = null

    @SerializedName("street")
    var street: String? = null

    @ColumnInfo(name="street_number")
    @SerializedName("streetNumber")
    var streetNumber: String? = null

    @ColumnInfo(name="zip_code")
    @SerializedName("zipCode")
    var zipCode: String? = null

    @ColumnInfo(name="city")
    @SerializedName("city")
    var city: String? = null

    @ColumnInfo(name="department")
    @SerializedName("department")
    var department: String? = null

    @ColumnInfo(name="country")
    @SerializedName("country")
    var country: String? = null

    @ColumnInfo(name="latitude")
    @SerializedName("latitude")
    var latitude: Double? = null

    @ColumnInfo(name="longitude")
    @SerializedName("longitude")
    var longitude: Double? = null

    @ColumnInfo(name="is_favorite")
    var isFavorite: Boolean = false
}
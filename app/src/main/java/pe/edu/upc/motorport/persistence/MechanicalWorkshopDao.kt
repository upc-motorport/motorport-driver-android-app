package pe.edu.upc.motorport.persistence

import androidx.room.Dao
import androidx.room.Query
import pe.edu.upc.motorport.models.MechanicalWorkshop

@Dao
interface MechanicalWorkshopDao {
    @Query("select id,name,average_rate,street,street_number,city,country,department,image_url,latitude,longitude,zip_code,is_favorite from mechanical_workshops where is_favorite=1")
    fun findFavorites(): MutableList<MechanicalWorkshop>
}
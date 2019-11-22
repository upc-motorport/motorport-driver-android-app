package pe.edu.upc.motorport.persistence

import androidx.room.*
import pe.edu.upc.motorport.models.MechanicalWorkshop

@Dao
interface MechanicalWorkshopDao {
    @Query("select id,name,average_rate,street,street_number,city,country,department,image_url,latitude,longitude,zip_code,is_favorite from mechanical_workshops where is_favorite=1")
    fun findFavorites(): MutableList<MechanicalWorkshop>

    @Query("select id,name,average_rate,street,street_number,city,country,department,image_url,latitude,longitude,zip_code,is_favorite from mechanical_workshops where id=:id")
    fun findById(id: Int): MutableList<MechanicalWorkshop>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(vararg workshops: MechanicalWorkshop)

    @Delete
    fun delete(vararg workshops: MechanicalWorkshop)
}
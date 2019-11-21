package pe.edu.upc.motorport.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.motorport.models.MechanicalWorkshop

@Database(entities =[MechanicalWorkshop::class],version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMechanicalWorkshopDao(): MechanicalWorkshopDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
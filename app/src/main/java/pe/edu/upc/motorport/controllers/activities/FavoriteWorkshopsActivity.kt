package pe.edu.upc.motorport.controllers.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite_workshops.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.adapters.FavoriteWorkshopsAdapter
import pe.edu.upc.motorport.models.MechanicalWorkshop
import pe.edu.upc.motorport.persistence.AppDatabase

class FavoriteWorkshopsActivity : AppCompatActivity() {

    lateinit var workshopAdapter: FavoriteWorkshopsAdapter
    var workshops: ArrayList<MechanicalWorkshop> = ArrayList<MechanicalWorkshop>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_workshops)
        this.supportActionBar?.title = getString(R.string.title_activity_favoriteworkshops)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvFavoriteWorkshops.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        getWorkshops()
    }

    private fun getWorkshops() {
        workshops = AppDatabase
            .getInstance(this)
            .getMechanicalWorkshopDao()
            .findFavorites() as ArrayList<MechanicalWorkshop>
        workshopAdapter = FavoriteWorkshopsAdapter(workshops,this)
        rvFavoriteWorkshops.adapter = workshopAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

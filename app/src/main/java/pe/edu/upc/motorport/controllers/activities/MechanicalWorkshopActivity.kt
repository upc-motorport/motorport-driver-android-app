package pe.edu.upc.motorport.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_mechanical_workshop.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.models.MechanicalWorkshop

class MechanicalWorkshopActivity : AppCompatActivity() {

    var workshop: MechanicalWorkshop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mechanical_workshop)
        val extra = intent.getSerializableExtra("workshop")
        var title: String
        if(extra != null){
            workshop = extra as MechanicalWorkshop
            title = workshop?.name!!
            load(workshop!!)
        }else{
            title = getString(R.string.title_activity_mechanicalworkshop)
        }
        this.supportActionBar?.title = title
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun load(workshop: MechanicalWorkshop){
        Picasso
            .get()
            .load(workshop.imageUrl)
            .placeholder(R.drawable.ic_directions_car_24px)
            .into(ivMechanicalWorkshop)
        rbAverageRate.rating = workshop.averageRate!!.toFloat()
        tvAverageRate.text = workshop.averageRate!!.toString()
        tvName.text = workshop.name
        tvCountry.text = workshop.country
        tvDepartment.text = workshop.department
        tvCity.text = workshop.city
        tvAddress.text = "${workshop.street} ${workshop.streetNumber}"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

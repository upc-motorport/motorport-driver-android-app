package pe.edu.upc.motorport.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upc.motorport.R

class VehicleFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_form)
        this.supportActionBar?.title = getString(R.string.title_activity_addvehicle)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

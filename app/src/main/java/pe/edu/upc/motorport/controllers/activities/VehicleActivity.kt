package pe.edu.upc.motorport.controllers.activities

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_vehicle.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.adapters.VehiclesAdapter
import pe.edu.upc.motorport.models.Vehicle
import pe.edu.upc.motorport.network.RetrofitSingleton
import pe.edu.upc.motorport.network.dto.ResultResponse
import pe.edu.upc.motorport.network.services.VehicleService
import pe.edu.upc.motorport.util.MotorportConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleActivity : AppCompatActivity() {

    lateinit var vehiclesAdapter: VehiclesAdapter
    var vehicles: List<Vehicle> = ArrayList<Vehicle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        this.supportActionBar?.title = getString(R.string.title_activity_myvehicles)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getVehicles()
        rvVehicles.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        fabAddVehicle.setOnClickListener{
            val intent = Intent(this,VehicleFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getVehicles(){
        val retrofit = RetrofitSingleton.instance
        val vehicleService = retrofit.create(VehicleService::class.java)
        val retrofitCall = vehicleService.findAll("Bearer ${MotorportConfig.MOTORPORT_TOKEN}")

        retrofitCall.enqueue(object: Callback<ResultResponse<List<Vehicle>>>{
            override fun onFailure(call: Call<ResultResponse<List<Vehicle>>>, t: Throwable) {
                Log.d("Exception: ", t.toString())
            }

            override fun onResponse(
                call: Call<ResultResponse<List<Vehicle>>>,
                response: Response<ResultResponse<List<Vehicle>>>
            ) {
                if(response.isSuccessful){
                    vehicles = response.body()!!.result!!
                    vehiclesAdapter = VehiclesAdapter(vehicles,this@VehicleActivity)
                    rvVehicles.adapter = vehiclesAdapter
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

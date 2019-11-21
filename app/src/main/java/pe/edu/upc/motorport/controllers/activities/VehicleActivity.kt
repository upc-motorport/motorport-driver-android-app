package pe.edu.upc.motorport.controllers.activities

import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    var vehicles: ArrayList<Vehicle> = ArrayList<Vehicle>()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        this.supportActionBar?.title = getString(R.string.title_activity_myvehicles)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
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
        val defaultToken = ""
        val token = sharedPreferences.getString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN,defaultToken)
        val authorizationHeader = "Bearer $token"
        val retrofitCall = vehicleService.findAll(authorizationHeader)

        retrofitCall.enqueue(object: Callback<ResultResponse<List<Vehicle>>>{
            override fun onFailure(call: Call<ResultResponse<List<Vehicle>>>, t: Throwable) {
                Log.d("Exception: ", t.toString())
            }

            override fun onResponse(
                call: Call<ResultResponse<List<Vehicle>>>,
                response: Response<ResultResponse<List<Vehicle>>>
            ) {
                if(response.isSuccessful){
                    vehicles = response.body()!!.result!! as ArrayList<Vehicle>
                    vehiclesAdapter = VehiclesAdapter(vehicles,this@VehicleActivity)
                    rvVehicles.adapter = vehiclesAdapter
                }
            }

        })
    }

    fun delete(id: Int){
        val retrofit = RetrofitSingleton.instance
        val vehicleService = retrofit.create(VehicleService::class.java)
        val defaultToken = ""
        val token = sharedPreferences.getString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN,defaultToken)
        val authorizationHeader = "Bearer $token"
        val retrofitCall = vehicleService.delete(authorizationHeader,id)
        retrofitCall.enqueue(object: Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Exception: ", t.toString())
            }
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(this@VehicleActivity,getString(R.string.message_ondelete_vehicle),
                        Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

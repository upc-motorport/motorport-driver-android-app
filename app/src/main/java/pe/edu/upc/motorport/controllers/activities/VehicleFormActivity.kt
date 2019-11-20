package pe.edu.upc.motorport.controllers.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_vehicle_form.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.models.Vehicle
import pe.edu.upc.motorport.network.RetrofitSingleton
import pe.edu.upc.motorport.network.dto.ResultResponse
import pe.edu.upc.motorport.network.dto.SaveVehicleRequest
import pe.edu.upc.motorport.network.services.VehicleService
import pe.edu.upc.motorport.util.MotorportConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleFormActivity : AppCompatActivity() {

    var vehicle: Vehicle? = null
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_form)
        this.supportActionBar?.title = getString(R.string.title_activity_addvehicle)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val extra = intent.getSerializableExtra("vehicle")
        if(extra != null){
            vehicle = extra as Vehicle
            load(vehicle!!)
        }
        btnRegisterVehicle.setOnClickListener{
            save()
        }
    }

    private fun save(){
        val type = tietType.text.toString()
        val brand = tietBrand.text.toString()
        val model = tietModel.text.toString()
        val registrationPlate = tietRegPlate.text.toString()
        val kilometers = tietKilometers.text.toString()
        var year = tietYear.text.toString()
        val isType = type.validator()
            .nonEmpty()
            .addErrorCallback {
                tietType.error = getString(R.string.message_empty_vehicletype)
            }
            .check()
        val isBrand = brand.validator()
            .nonEmpty()
            .addErrorCallback {
                tietBrand.error = getString(R.string.message_empty_vehiclebrand)
            }
            .check()
        val isModel = model.validator()
            .nonEmpty()
            .addErrorCallback {
                tietModel.error = getString(R.string.message_empty_model)
            }
            .check()

        val isRegistrationPlate = registrationPlate.validator()
            .nonEmpty()
            .addErrorCallback {
                tietRegPlate.error = getString(R.string.message_empty_regplate)
            }
            .check()

        val isYear = year.validator()
            .nonEmpty()
            .addErrorCallback {
                tietYear.error = getString(R.string.message_empty_year)
            }
            .check()

        val isKilometers = kilometers.validator()
            .nonEmpty()
            .addErrorCallback {
                tietKilometers.error = getString(R.string.message_empty_kilometers)
            }
            .check()

        if(isType && isBrand && isModel && isRegistrationPlate && isYear && isKilometers){
            var imageUrl: String?
            var postMethod: Boolean = true
            if(vehicle == null){
                imageUrl = null
            }else{
                imageUrl = vehicle!!.imageUrl
                postMethod = false
            }
            val requestBody = SaveVehicleRequest(registrationPlate,0,model,brand,type,year.toInt(),kilometers.toInt(),imageUrl!!)
            val retrofit = RetrofitSingleton.instance
            val vehicleService = retrofit.create(VehicleService::class.java)
            val defaultToken = ""
            val token = sharedPreferences.getString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN,defaultToken)
            val authorizationHeader = "Bearer $token"
            if(postMethod){
                val retrofitCall = vehicleService.save(authorizationHeader,requestBody)
                retrofitCall.enqueue(object: Callback<ResultResponse<Int>>{
                    override fun onFailure(call: Call<ResultResponse<Int>>, t: Throwable) {
                        Log.d("Exception: ", t.toString())
                    }

                    override fun onResponse(
                        call: Call<ResultResponse<Int>>,
                        response: Response<ResultResponse<Int>>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(this@VehicleFormActivity,getString(R.string.message_onsave_vehicle),Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }else{
                val retrofitCall = vehicleService.update(authorizationHeader,requestBody,vehicle!!.id!!)
                retrofitCall.enqueue(object: Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("Exception: ", t.toString())
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(this@VehicleFormActivity,getString(R.string.message_onupdate_vehicle),Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
            val intent = Intent(this@VehicleFormActivity,VehicleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun load(vehicle: Vehicle){
        Picasso
            .get()
            .load(vehicle.imageUrl)
            .placeholder(R.drawable.ic_directions_car_24px)
            .into(ivVehicle)
        tietBrand.setText(vehicle.brand)
        tietType.setText(vehicle.type)
        tietModel.setText(vehicle.model)
        tietRegPlate.setText(vehicle.registrationPlate)
        tietKilometers.setText(vehicle.kilometers.toString())
        tietYear.setText(vehicle.year.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

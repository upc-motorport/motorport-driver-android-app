package pe.edu.upc.motorport.controllers.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_mechanical_workshops.*

import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.adapters.MechanicalWorkshopsAdapter
import pe.edu.upc.motorport.models.MechanicalWorkshop
import pe.edu.upc.motorport.network.RetrofitSingleton
import pe.edu.upc.motorport.network.dto.ResultResponse
import pe.edu.upc.motorport.network.services.MechanicalWorkshopService
import pe.edu.upc.motorport.util.MotorportConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MechanicalWorkshopsFragment : Fragment() {

    lateinit var workshopAdapter: MechanicalWorkshopsAdapter
    lateinit var sharedPreferences: SharedPreferences
    var workshops: ArrayList<MechanicalWorkshop> = ArrayList<MechanicalWorkshop>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mechanical_workshops, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = view.context.getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        rvWorkshops.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        getWorkshops()
    }

    private fun getWorkshops(){
        val retrofit = RetrofitSingleton.instance
        val workshopService = retrofit.create(MechanicalWorkshopService::class.java)
        val defaultToken = ""
        val token = sharedPreferences.getString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN,defaultToken)
        val authorizationHeader = "Bearer $token"
        val retrofitCall = workshopService.findAll(authorizationHeader)
        retrofitCall.enqueue(object: Callback<ResultResponse<List<MechanicalWorkshop>>>{
            override fun onFailure(
                call: Call<ResultResponse<List<MechanicalWorkshop>>>,
                t: Throwable
            ) {
                Log.d("Exception: ", t.toString())
            }
            override fun onResponse(
                call: Call<ResultResponse<List<MechanicalWorkshop>>>,
                response: Response<ResultResponse<List<MechanicalWorkshop>>>
            ) {
                if(response.isSuccessful){
                    workshops = response.body()!!.result!! as ArrayList<MechanicalWorkshop>
                    workshopAdapter = MechanicalWorkshopsAdapter(workshops,context!!)
                    rvWorkshops.adapter = workshopAdapter
                }
            }

        })
    }
}

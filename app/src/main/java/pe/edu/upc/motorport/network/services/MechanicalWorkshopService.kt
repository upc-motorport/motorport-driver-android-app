package pe.edu.upc.motorport.network.services

import pe.edu.upc.motorport.models.MechanicalWorkshop
import pe.edu.upc.motorport.network.dto.ResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MechanicalWorkshopService {
    @GET("mechanicalworkshops")
    fun findAll(@Header("Authorization") authorization: String): Call<ResultResponse<List<MechanicalWorkshop>>>

    @GET("mechanicalworkshops/{id}")
    fun findById(@Header("Authorization") authorization: String): Call<ResultResponse<MechanicalWorkshop>>
}
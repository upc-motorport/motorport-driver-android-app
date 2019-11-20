package pe.edu.upc.motorport.network.services

import pe.edu.upc.motorport.models.Vehicle
import pe.edu.upc.motorport.network.dto.ResultResponse
import pe.edu.upc.motorport.network.dto.SaveVehicleRequest
import retrofit2.Call
import retrofit2.http.*

interface VehicleService {
    @GET("vehicles")
    fun findAll(@Header("Authorization") authorization: String): Call<ResultResponse<List<Vehicle>>>

    @GET("vehicles/{id}")
    fun findById(@Header("Authorization") authorization: String, @Path("id") id: Int): Call<ResultResponse<Vehicle>>

    @POST("vehicles")
    fun save(@Header("Authorization") authorization: String, @Body vehicle: SaveVehicleRequest): Call<ResultResponse<Int>>

    @PUT("vehicles/{id}")
    fun update(@Header("Authorization") authorization: String, @Body vehicle: SaveVehicleRequest, @Path("id") id: Int): Call<Void>

    @DELETE("vehicles/{id}")
    fun delete(@Header("Authorization") authorization: String, @Path("id") id: Int): Call<Void>
}
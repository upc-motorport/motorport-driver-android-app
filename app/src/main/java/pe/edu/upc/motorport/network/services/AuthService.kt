package pe.edu.upc.motorport.network.services

import pe.edu.upc.motorport.network.dto.LoginRequest
import pe.edu.upc.motorport.network.dto.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
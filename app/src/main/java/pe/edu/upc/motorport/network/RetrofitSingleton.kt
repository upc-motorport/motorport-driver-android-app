package pe.edu.upc.motorport.network

import pe.edu.upc.motorport.util.MotorportConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitSingleton{
    companion object {
        private var INSTANCE: Retrofit? = null

        val instance: Retrofit get() {
            if(INSTANCE == null){
                INSTANCE = Retrofit.Builder()
                    .baseUrl(MotorportConfig.API_V1_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }
    }
}
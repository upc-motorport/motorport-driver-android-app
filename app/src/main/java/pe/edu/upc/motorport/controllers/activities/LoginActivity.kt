package pe.edu.upc.motorport.controllers.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_login.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.network.RetrofitSingleton
import pe.edu.upc.motorport.network.dto.LoginRequest
import pe.edu.upc.motorport.network.dto.LoginResponse
import pe.edu.upc.motorport.network.services.AuthService
import pe.edu.upc.motorport.util.MotorportConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.supportActionBar?.title = getString(R.string.title_activity_login)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        btnLogin.setOnClickListener{
            onClickLogin()
        }
    }

    private fun onClickLogin(){
        val email = tietEmail.text.toString()
        val password = tietPassword.text.toString()
        val isEmail = email.validEmail(){
            tietEmail.error = getString(R.string.message_invalid_email)
        }

        val isPassword = password.validator()
            .nonEmpty()
            .addErrorCallback {
                tietPassword.error = getString(R.string.message_empty_password)
            }.check()

        if(isPassword && isEmail){
            val retrofit = RetrofitSingleton.instance
            val authService = retrofit.create(AuthService::class.java)
            val requestBody = LoginRequest(email,password)
            val retrofitCall = authService.login(requestBody)

            retrofitCall.enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Exception: ", t.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful) {
                        val token = response.body()!!.token
                        val editor = sharedPreferences.edit()
                        editor.putString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN, token)
                        editor.commit()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else if(response.code() == 400){
                        Toast.makeText(this@LoginActivity,getString(R.string.message_login_error),Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

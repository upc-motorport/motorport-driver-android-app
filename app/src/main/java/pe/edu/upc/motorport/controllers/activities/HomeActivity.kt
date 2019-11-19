package pe.edu.upc.motorport.controllers.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.util.MotorportConfig

class HomeActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sharedPreferences = getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val defaultToken = ""
        val token = sharedPreferences.getString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN,defaultToken)
        if(token!!.isNotEmpty()){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

package pe.edu.upc.motorport.controllers.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account.*

import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.controllers.activities.HomeActivity
import pe.edu.upc.motorport.controllers.activities.VehicleActivity
import pe.edu.upc.motorport.util.MotorportConfig

class AccountFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = view.context.getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        btnMyVehicles.setOnClickListener{
            val intent = Intent(context, VehicleActivity::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.remove(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN)
            editor.apply()
            val intent = Intent(context,HomeActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }
    }
}

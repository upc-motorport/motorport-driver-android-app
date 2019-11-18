package pe.edu.upc.motorport.controllers.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account.*

import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.controllers.activities.VehicleActivity

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnMyVehicles.setOnClickListener{
            val intent = Intent(context, VehicleActivity::class.java)
            startActivity(intent)
        }
    }
}

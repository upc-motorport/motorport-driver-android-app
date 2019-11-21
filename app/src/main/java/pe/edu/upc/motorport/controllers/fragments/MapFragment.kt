package pe.edu.upc.motorport.controllers.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.adapters.WorkshopInfoWindowAdapter
import pe.edu.upc.motorport.controllers.activities.MechanicalWorkshopActivity
import pe.edu.upc.motorport.models.MechanicalWorkshop
import pe.edu.upc.motorport.network.RetrofitSingleton
import pe.edu.upc.motorport.network.dto.ResultResponse
import pe.edu.upc.motorport.network.services.MechanicalWorkshopService
import pe.edu.upc.motorport.util.MotorportConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = view.context.getSharedPreferences(MotorportConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        map.mapType = GoogleMap.MAP_TYPE_NORMAL;
        map.clear(); //clear old markers
        map.uiSettings.isZoomControlsEnabled = true
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15.0f))
            }
        }
        val infoWindowAdapter = WorkshopInfoWindowAdapter(context!!)
        map.setInfoWindowAdapter(infoWindowAdapter)
        getWorkshops(map)
        map.setOnInfoWindowClickListener {
            val workshop = it.tag as MechanicalWorkshop
            val intent = Intent(context,MechanicalWorkshopActivity::class.java)
            intent.putExtra("workshop",workshop)
            startActivity(intent)
        }
        setUpMap()
    }

    private fun getWorkshops(map: GoogleMap){
        val retrofit = RetrofitSingleton.instance
        val workshopService = retrofit.create(MechanicalWorkshopService::class.java)
        val defaultToken = ""
        val token = sharedPreferences.getString(MotorportConfig.SHARED_PREFERENCES_FIELD_TOKEN,defaultToken)
        val authorizationHeader = "Bearer $token"
        val retrofitCall = workshopService.findAll(authorizationHeader)
        retrofitCall.enqueue(object: Callback<ResultResponse<List<MechanicalWorkshop>>> {
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
                    val workshops: List<MechanicalWorkshop> = response.body()!!.result!!
                    for (workshop in workshops){
                        val markerOptions = MarkerOptions()
                        markerOptions
                            .position(LatLng(workshop.latitude!!,workshop.longitude!!))
                            .title(workshop.name)
                            .snippet("${workshop.street} ${workshop.streetNumber} - ${workshop.city}, ${workshop.department}")
                        map.addMarker(markerOptions).tag = workshop
                    }
                }
            }

        })
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }
}

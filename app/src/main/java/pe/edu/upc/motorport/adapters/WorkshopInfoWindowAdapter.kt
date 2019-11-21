package pe.edu.upc.motorport.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.infowindow_workshop.view.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.models.MechanicalWorkshop

class WorkshopInfoWindowAdapter(val context: Context): GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.infowindow_workshop,null)
        val workshop = marker!!.tag as MechanicalWorkshop
        view.tvName.text = workshop.name
        view.tvAddress.text = "${workshop.street} ${workshop.streetNumber}"
        Picasso
            .get()
            .load(workshop.imageUrl)
            .placeholder(R.drawable.ic_directions_car_24px)
            .into(view.ivWorkshop)
        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}
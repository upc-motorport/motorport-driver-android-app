package pe.edu.upc.motorport.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_vehicle.view.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.models.Vehicle

class VehiclesAdapter(var vehicles: List<Vehicle>, var context: Context): RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder>() {
    inner class VehicleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val vehicleImageView = itemView.ivVehicle
        val nameTextView = itemView.tvVehicleName
        val registrationPlateTextView = itemView.tvRegistrationPlate
        fun bindTo(vehicle: Vehicle){
            Picasso
                .get()
                .load(vehicle.imageUrl)
                .placeholder(R.drawable.ic_directions_car_24px)
                .into(vehicleImageView)
            nameTextView.text = "${vehicle.brand?.toUpperCase()} ${vehicle.model?.toUpperCase()}"
            registrationPlateTextView.text = vehicle.registrationPlate!!.toUpperCase()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_vehicle,parent,false))
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bindTo(vehicles[position])
    }

}
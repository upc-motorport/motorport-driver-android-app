package pe.edu.upc.motorport.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_mechanical_workshop.view.*
import pe.edu.upc.motorport.R
import pe.edu.upc.motorport.controllers.activities.MechanicalWorkshopActivity
import pe.edu.upc.motorport.models.MechanicalWorkshop
import pe.edu.upc.motorport.persistence.AppDatabase
import pe.edu.upc.motorport.util.MotorportConfig

class MechanicalWorkshopsAdapter(private var workshops: ArrayList<MechanicalWorkshop>, val context:Context): RecyclerView.Adapter<MechanicalWorkshopsAdapter.MechanicalWorkshopViewHolder>() {
    inner class MechanicalWorkshopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val ivMechanicalWorkshop = itemView.ivMechanicalWorkshop
        private val nameTextView = itemView.tvMechWorkshopName
        private val addressTextView = itemView.tvMechWorkshopAddress

        fun bindTo(workshop: MechanicalWorkshop){
            Picasso.get()
                .load(workshop.imageUrl)
                .placeholder(R.drawable.ic_directions_car_24px)
                .into(ivMechanicalWorkshop)
            nameTextView.text = workshop.name
            addressTextView.text = "${workshop.city},${workshop.department}"
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MechanicalWorkshopViewHolder {
        return MechanicalWorkshopViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_mechanical_workshop,parent,false))
    }

    override fun getItemCount(): Int {
        return workshops.size
    }

    override fun onBindViewHolder(holder: MechanicalWorkshopViewHolder, position: Int) {
        val workshop = workshops[position]
        holder.bindTo(workshop)
        workshop.isFavorite = AppDatabase
            .getInstance(context)
            .getMechanicalWorkshopDao()
            .findById(workshop.id!!)
            .isNotEmpty()
        holder.itemView.ibFavorite.setImageResource(MotorportConfig.getResourceForFavoriteImageButton(workshop.isFavorite))

        holder.itemView.cvMechanicalWorkshop.setOnClickListener{
            val intent = Intent(context,MechanicalWorkshopActivity::class.java)
            intent.putExtra("workshop",workshop)
            context.startActivity(intent)
        }
        holder.itemView.ibFavorite.setOnClickListener {
            if(workshop.isFavorite){
                AppDatabase
                    .getInstance(context)
                    .getMechanicalWorkshopDao()
                    .delete(workshop)
            }else{
                workshop.isFavorite = true
                AppDatabase
                    .getInstance(context)
                    .getMechanicalWorkshopDao()
                    .save(workshop)
                Toast.makeText(context,"${workshop.name} agregado a mecánicos de confianza",Toast.LENGTH_SHORT).show()
            }
            notifyItemChanged(position)
        }
    }
}
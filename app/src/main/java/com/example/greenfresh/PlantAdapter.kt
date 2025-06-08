package com.example.greenfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenfresh.R
import com.example.greenfresh.Plant

class PlantAdapter(
    private var plants: List<Plant>,
    private val onDeleteClick: (Plant) -> Unit,
    private val onDetailClick: (Plant) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.iv_plant_image)
        val plantName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val plantPrice: TextView = itemView.findViewById(R.id.tv_plant_price)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete)
        val detailButton: Button = itemView.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]

        holder.plantName.text = plant.plantName
        holder.plantPrice.text = plant.getFormattedPrice()

        // Set static image based on plant name or use default
        val imageResource = getPlantImageResource(plant.plantName)
        holder.plantImage.setImageResource(imageResource)

        holder.deleteButton.setOnClickListener {
            onDeleteClick(plant)
        }

        holder.detailButton.setOnClickListener {
            onDetailClick(plant)
        }
    }

    override fun getItemCount(): Int = plants.size

    fun updatePlants(newPlants: List<Plant>) {
        plants = newPlants
        notifyDataSetChanged()
    }

    private fun getPlantImageResource(plantName: String): Int {
        return when (plantName.lowercase()) {
            else -> R.drawable.plant // Default plant image
        }
    }
}
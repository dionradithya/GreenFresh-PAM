package com.example.greenfresh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlantDetailActivity : AppCompatActivity() {

    private lateinit var plantImage: ImageView
    private lateinit var plantNameTextView: TextView
    private lateinit var plantPriceTextView: TextView
    private lateinit var plantDescriptionTextView: TextView
    private lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        initViews()
        loadPlantData()
        setupClickListeners()
    }

    private fun initViews() {
        plantImage = findViewById(R.id.iv_plant_image)
        plantNameTextView = findViewById(R.id.tv_plant_name)
        plantPriceTextView = findViewById(R.id.tv_plant_price)
        plantDescriptionTextView = findViewById(R.id.tv_plant_description)
        updateButton = findViewById(R.id.btn_update)

        plantImage.setImageResource(R.drawable.plant)
    }

    private fun loadPlantData() {
        val plantName = intent.getStringExtra("plant_name") ?: "Unknown Plant"
        val price = intent.getStringExtra("price") ?: "Rp 0"
        val description = intent.getStringExtra("description") ?: "No description available"

        plantNameTextView.text = plantName
        // Format price if needed, but since we're passing raw price, use it directly
        plantPriceTextView.text = try {
            "Rp ${price.toLongOrNull()?.let { String.format("%,d", it).replace(',', '.') } ?: price}"
        } catch (e: NumberFormatException) {
            "Rp $price"
        }
        plantDescriptionTextView.text = description
    }

    private fun setupClickListeners() {
        updateButton.setOnClickListener {
            val plantId = intent.getIntExtra("plant_id", 0)
            val plantName = plantNameTextView.text.toString()
            val price = plantPriceTextView.text.toString().replace("Rp ", "").replace(".", "") // Remove formatting
            val description = plantDescriptionTextView.text.toString()

            val intent = Intent(this, UpdatePlantActivity::class.java).apply {
                putExtra("plant_id", plantId)
                putExtra("plant_name", plantName)
                putExtra("price", price)
                putExtra("description", description)
            }
            startActivity(intent)
        }
    }
}
package com.example.greenfresh

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class UpdatePlantActivity : AppCompatActivity() {

    private lateinit var plantImage: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var updateButton: Button
    private var plantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_plant)

        initViews()
        loadPlantData()
        setupClickListeners()
    }

    private fun initViews() {
        plantImage = findViewById(R.id.iv_plant_image)
        nameEditText = findViewById(R.id.et_plant_name)
        priceEditText = findViewById(R.id.et_price)
        descriptionEditText = findViewById(R.id.et_description)
        updateButton = findViewById(R.id.btn_simpan)

        plantImage.setImageResource(R.drawable.plant)
    }

    private fun loadPlantData() {
        plantId = intent.getIntExtra("plant_id", 0)
        val plantName = intent.getStringExtra("plant_name") ?: ""
        val price = intent.getStringExtra("price") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        nameEditText.setText(plantName)
        priceEditText.setText(price)
        descriptionEditText.setText(description)
    }

    private fun setupClickListeners() {
        updateButton.setOnClickListener {
            val plantName = nameEditText.text.toString().trim()
            val price = priceEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            if (validateInputs(plantName, price, description)) {
                updatePlant(plantName, description, price)
            }
        }
    }

    private fun validateInputs(name: String, price: String, description: String): Boolean {
        if (name.isEmpty()) {
            nameEditText.error = "Nama tanaman tidak boleh kosong"
            return false
        }
        if (price.isEmpty()) {
            priceEditText.error = "Harga tidak boleh kosong"
            return false
        }
        if (description.isEmpty()) {
            descriptionEditText.error = "Deskripsi tidak boleh kosong"
            return false
        }
        return true
    }

    private fun updatePlant(name: String, description: String, price: String) {
        val request = CreatePlantRequest(name, description, price)
        RetrofitClient.apiService.updatePlant(name, request).enqueue(object : retrofit2.Callback<SinglePlantResponse> {
            override fun onResponse(call: retrofit2.Call<SinglePlantResponse>, response: retrofit2.Response<SinglePlantResponse>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: retrofit2.Call<SinglePlantResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
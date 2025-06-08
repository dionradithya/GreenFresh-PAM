package com.example.greenfresh

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.content.Intent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlantActivity : AppCompatActivity() {

    private lateinit var plantImage: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        plantImage = findViewById(R.id.iv_plant_image)
        nameEditText = findViewById(R.id.et_plant_name)
        priceEditText = findViewById(R.id.et_price)
        descriptionEditText = findViewById(R.id.et_description)
        addButton = findViewById(R.id.btn_tambah)

        plantImage.setImageResource(R.drawable.plant)
    }

    private fun setupClickListeners() {
        addButton.setOnClickListener {
            val plantName = nameEditText.text.toString().trim()
            val price = priceEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            if (validateInputs(plantName, price, description)) {
                createPlant(plantName, description, price)
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

    private fun createPlant(name: String, description: String, price: String) {
        val request = CreatePlantRequest(name, description, price)
        RetrofitClient.apiService.createPlant(request).enqueue(object : Callback<SinglePlantResponse> {
            override fun onResponse(call: Call<SinglePlantResponse>, response: Response<SinglePlantResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddPlantActivity, "Tanaman berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddPlantActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // Handle error
                    Toast.makeText(this@AddPlantActivity, "Gagal menambahkan tanaman: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SinglePlantResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@AddPlantActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
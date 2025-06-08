package com.example.greenfresh.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenfresh.PlantAdapter
import com.example.greenfresh.R
import com.example.greenfresh.api.RetrofitClient
import com.example.greenfresh.model.Plant
import com.example.greenfresh.model.PlantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var plantAdapter: PlantAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var btnTambahList: Button

    private val plants = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupRecyclerView()
        loadPlants()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.rv_plants)
        progressBar = findViewById(R.id.progress_bar)
        btnTambahList = findViewById(R.id.btn_tambah_list)

        btnTambahList.setOnClickListener {
             val intent = Intent(this, AddPlantActivity::class.java)
             startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        plantAdapter = PlantAdapter(
            plants = plants,
            onDeleteClick = { plant -> showDeleteConfirmation(plant) },
            onDetailClick = { plant -> showPlantDetail(plant) }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = plantAdapter
        }
    }

    private fun loadPlants() {
        showLoading(true)

        RetrofitClient.apiService.getAllPlants().enqueue(object : Callback<PlantResponse> {
            override fun onResponse(call: Call<PlantResponse>, response: Response<PlantResponse>) {
                showLoading(false)

                if (response.isSuccessful) {
                    response.body()?.let { plantResponse ->
                        plants.clear()
                        plants.addAll(plantResponse.data)
                        plantAdapter.updatePlants(plants)

                        if (plants.isEmpty()) {
                            showEmptyState()
                        }
                    } ?: showError("No data received")
                } else {
                    showError("Gagal memuat data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PlantResponse>, t: Throwable) {
                showLoading(false)
                showError("Tidak dapat terhubung ke server: ${t.message}")
            }
        })
    }

    private fun showDeleteConfirmation(plant: Plant) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Tanaman")
            .setMessage("Apakah Anda yakin ingin menghapus ${plant.plantName}?")
            .setPositiveButton("Hapus") { _, _ ->
                deletePlant(plant)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deletePlant(plant: Plant) {
        showLoading(true)

        RetrofitClient.apiService.deletePlant(plant.plantName).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                showLoading(false)

                if (response.isSuccessful) {
                    Toast.makeText(this@HomeActivity, "${plant.plantName} berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadPlants()
                } else {
                    showError("Gagal menghapus tanaman: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showLoading(false)
                showError("Tidak dapat terhubung ke server: ${t.message}")
            }
        })
    }

    private fun showPlantDetail(plant: Plant) {
        val intent = Intent(this, PlantDetailActivity::class.java)
        intent.putExtra("plant_id", plant.id)
        intent.putExtra("plant_name", plant.plantName ?: "")
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showEmptyState() {
        Toast.makeText(this, "Belum ada tanaman tersedia", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadPlants()
    }
}
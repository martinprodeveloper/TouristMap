package com.martinprodeveloper.touristmap.ui.home.map

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.martinprodeveloper.touristmap.R
import com.martinprodeveloper.touristmap.databinding.ActivityHomeMapBinding
import com.martinprodeveloper.touristmap.domain.model.TouristPlace

class HomeMapActivity : AppCompatActivity() {

    private val TAG = "HomeMapActivity"
    private lateinit var binding: ActivityHomeMapBinding
    var touristPlace: TouristPlace? = null
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_home_map)

        touristPlace = gson.fromJson(intent.getStringExtra("tourist_place"), TouristPlace::class.java)
        Log.d(TAG, "JSON recibido: ${touristPlace?.toJson()}")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        //
    }

    private fun setUpObservers() {
        //
    }
}
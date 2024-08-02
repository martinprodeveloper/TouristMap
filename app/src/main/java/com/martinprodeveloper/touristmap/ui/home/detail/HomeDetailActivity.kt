package com.martinprodeveloper.touristmap.ui.home.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.martinprodeveloper.touristmap.R
import com.martinprodeveloper.touristmap.databinding.ActivityHomeDetailBinding
import com.martinprodeveloper.touristmap.domain.model.TouristPlace

class HomeDetailActivity : AppCompatActivity() {

    private val TAG = "HomeDetailActivity"
    private lateinit var binding: ActivityHomeDetailBinding
    var touristPlace: TouristPlace? = null
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
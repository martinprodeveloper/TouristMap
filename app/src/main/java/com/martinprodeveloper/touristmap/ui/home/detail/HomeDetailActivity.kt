package com.martinprodeveloper.touristmap.ui.home.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.martinprodeveloper.touristmap.R
import com.martinprodeveloper.touristmap.databinding.ActivityHomeDetailBinding
import com.martinprodeveloper.touristmap.domain.model.TouristPlace
import com.martinprodeveloper.touristmap.ui.home.map.HomeMapActivity

class HomeDetailActivity : AppCompatActivity() {

    private val TAG = "HomeDetailActivity"
    private lateinit var binding: ActivityHomeDetailBinding
    var touristPlace: TouristPlace? = null
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        Glide.with(binding.root.context)
            .load(touristPlace?.imageUrl)
            .placeholder(R.drawable.ic_tourist_place_image_placeholder)
            .error(R.drawable.ic_tourist_place_image_placeholder)
            .into(binding.ivTouristPlaceImage)

        binding.tvName?.text = touristPlace?.name
        binding.tvDescription?.text = touristPlace?.description

        val attractions = touristPlace?.attractions?.let {
            String.format(
                getString(R.string.home_detail_attractions_format),
                it.attraction1 ?: "",
                it.attraction2 ?: "",
                it.attraction3 ?: "",
                it.attraction4 ?: ""
            )
        } ?: ""
        binding.tvAttractions?.text = attractions

        binding.btnMap.setOnClickListener {
            goToMap()
        }
    }

    private fun setUpObservers() {
        //
    }

    private fun goToMap(){
        val i = Intent(this, HomeMapActivity::class.java)
        i.putExtra("tourist_place", touristPlace?.toJson())
        startActivity(i)
    }
}
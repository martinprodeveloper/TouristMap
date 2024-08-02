package com.martinprodeveloper.touristmap.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.martinprodeveloper.touristmap.R
import com.martinprodeveloper.touristmap.databinding.ActivityHomeBinding
import com.martinprodeveloper.touristmap.databinding.ToolbarHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    private lateinit var toolbarBinding: ToolbarHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val homeAdapter by lazy {
        HomeAdapter(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarBinding = ToolbarHomeBinding.bind(binding.root.findViewById(R.id.included_toolbar))

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.rvTouristPlaces?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
        binding.svTouristPlaces?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    homeViewModel.filterTouristPlaces(it)
                }
                return true
            }
        })
        toolbarBinding.ivGithub.setOnClickListener {
            val uri = Uri.parse("https://github.com/martinprodeveloper")
            startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW, uri), "Open with"))
            Log.d(TAG, "GitHub button clicked")
        }
    }

    private fun setUpObservers() {
        homeViewModel.touristPlacesList.observe(this) { touristPlacesList ->
            // Verificar que touristPlacesList no sea null
            touristPlacesList?.let {
                homeAdapter.updateTouristPlacesList(it)
            }
        }
        homeViewModel.isLoading.observe(this) { isLoading ->
            // Show or hide a loading indicator based on isLoading
        }
        homeViewModel.onError.observe(this) { hasError ->
            if (hasError) {
                // Handle error state (e.g., show a Toast or Snackbar)
            }
        }
    }
}
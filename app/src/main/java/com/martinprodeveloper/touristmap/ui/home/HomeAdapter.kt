package com.martinprodeveloper.touristmap.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martinprodeveloper.touristmap.R
import com.martinprodeveloper.touristmap.databinding.ItemTouristPlaceCardBinding
import com.martinprodeveloper.touristmap.domain.model.TouristPlace
import com.martinprodeveloper.touristmap.ui.home.detail.HomeDetailActivity

class HomeAdapter(
    private var touristPlaces: List<TouristPlace>,
) : RecyclerView.Adapter<HomeAdapter.TouristPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristPlaceViewHolder {
        val binding = ItemTouristPlaceCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TouristPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TouristPlaceViewHolder, position: Int) {
        val touristPlace = touristPlaces[position]
        holder.bind(touristPlace)
    }

    override fun getItemCount(): Int = touristPlaces.size

    fun updateTouristPlacesList(newTouristPlaces: List<TouristPlace>) {
        touristPlaces = newTouristPlaces
        notifyDataSetChanged()
    }

    class TouristPlaceViewHolder(
        private val binding: ItemTouristPlaceCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(touristPlace: TouristPlace) {
            Glide.with(binding.root.context)
                .load(touristPlace.imageUrl)
                .placeholder(R.drawable.ic_tourist_place_image_placeholder)
                .error(R.drawable.ic_tourist_place_image_placeholder)
                .into(binding.ivTouristPlaceImage)

            binding.tvTouristPlaceName.text = touristPlace.name
            binding.tvTouristPlaceDescription.text = touristPlace.description
            binding.cvTouristPlace.setOnClickListener {
                goToDetail(touristPlace)
            }
        }

        fun goToDetail(touristPlace: TouristPlace){
            val i = Intent(binding.root.context, HomeDetailActivity::class.java)
            i.putExtra("tourist_place", touristPlace.toJson())
            binding.root.context.startActivity(i)
        }
    }
}
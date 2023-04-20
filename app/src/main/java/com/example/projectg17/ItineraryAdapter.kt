package com.example.projectg17

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectg17.models.*


class ItineraryAdapter(private val itineraryList: List<Itinerary>) :
    RecyclerView.Adapter<ItineraryAdapter.ItineraryViewHolder>() {

    inner class ItineraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parkNameTextView: TextView = itemView.findViewById(R.id.park_name)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
        val notesTextView: TextView = itemView.findViewById(R.id.notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_itinerary, parent, false)
        return ItineraryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        val currentItem = itineraryList[position]

        holder.parkNameTextView.text = currentItem.name
        holder.dateTextView.text = "Date: ${currentItem.date}"
        holder.notesTextView.text = "Notes: ${currentItem.notes}"
    }

    override fun getItemCount() = itineraryList.size
}


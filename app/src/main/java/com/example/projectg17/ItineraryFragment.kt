package com.example.projectg17

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectg17.models.*

import com.example.projectg17.databinding.FragmentItineraryBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItineraryFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var itineraryAdapter: ItineraryAdapter

    private var _binding: FragmentItineraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the Firestore instance
        firestore = FirebaseFirestore.getInstance()

        // Create the adapter for the RecyclerView
        itineraryAdapter = ItineraryAdapter(emptyList())

        // Set up the RecyclerView
        binding.itineraryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itineraryAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        // Query the "itinerary" collection in Firestore
        firestore.collection("itinerary")
            .get()
            .addOnSuccessListener { documents ->
                // Create a list of Itinerary objects from the documents
                val itineraryList = mutableListOf<Itinerary>()
                for (document in documents) {
                    val parkName = document.getString("name") ?: "No  Park"
                    val date = document.getString("date") ?: ""
                    val notes = document.getString("notes") ?: ""
                    val id = document.id
                    if (parkName != null) {
                        val itinerary = Itinerary(parkName, date, notes, id)
                        itineraryList.add(itinerary)
                    }
                }
                // Update the adapter with the new list of Itinerary objects
                itineraryAdapter.updateItineraryList(itineraryList)
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Toast.makeText(requireContext(), "Failed to retrieve itinerary: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_settings -> {
                val toast = Toast.makeText(requireContext(), "Settings Menu clicked!", Toast.LENGTH_SHORT)
                toast.show()
                return true
            }
            R.id.mi_profile -> {
                val toast = Toast.makeText(requireContext(), "Profile Menu clicked!", Toast.LENGTH_SHORT)
                toast.show()
                return true
            }
            R.id.mi_logout -> {
                val toast = Toast.makeText(requireContext(), "Logout Menu clicked!", Toast.LENGTH_SHORT)
                toast.show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

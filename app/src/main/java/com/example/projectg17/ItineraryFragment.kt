package com.example.projectg17

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectg17.databinding.FragmentItineraryBinding
import com.example.projectg17.models.Itinerary
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.fragment.findNavController

class ItineraryFragment : Fragment(R.layout.fragment_itinerary) {

    private lateinit var myArrayAdapter:ArrayAdapter<String>
    private val userItineraryList:MutableList<String> = mutableListOf("Itinerary 1", "Itinerary 2", "Itinerary 3")

    // binding variables
    private var _binding: FragmentItineraryBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore

      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          // binding
          _binding = FragmentItineraryBinding.inflate(inflater, container, false)
          val view = binding.root
          return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var parkName = ""
        var Date = ""
        var Addres = ""
        var Notes = ""
        val itineraryList = mutableListOf<Itinerary>()

        // Initialize the Firestore instance
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("itinerary")
            .get()
            .addOnSuccessListener { documents ->
                userItineraryList.clear()
                for (document in documents) {
                    parkName = document.getString("name") ?: "No  Park"
                    Date = document.getString("dateofvisit") ?: ""
                    Addres = document.getString("address") ?: "141 Dess"
                    Notes = document.getString("notes") ?: ""
                    val id = document.id
                    if (parkName != null) {
                        val parkDetails = "${parkName}\n\n${Addres}\n\n${Date}\n"
                        userItineraryList.add(parkDetails)

                        val itinerary = Itinerary(parkName, Addres, Date, Notes, id)
                        itineraryList.add(itinerary)
                    }
                }
                myArrayAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve itinerary: $exception", Toast.LENGTH_SHORT).show()
            }

        this.myArrayAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,userItineraryList)
        binding.listView.adapter = myArrayAdapter
        binding.listView.setOnItemClickListener { adapterView, view, i, l ->
            val action = ItineraryFragmentDirections.actionItineraryFragmentToItineraryDetailFragment(
                itineraryList[i].name,
                itineraryList[i].address,
                itineraryList[i].date,
                itineraryList[i].notes,
            )
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val inflater: MenuInflater = inflater
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
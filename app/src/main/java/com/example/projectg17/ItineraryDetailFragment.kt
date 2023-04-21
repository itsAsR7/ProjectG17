package com.example.projectg17

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projectg17.databinding.FragmentItineraryDetailBinding
import com.google.firebase.firestore.FirebaseFirestore

class ItineraryDetailFragment : Fragment(R.layout.fragment_itinerary) {

    // binding variables
    private var _binding: FragmentItineraryDetailBinding? = null
    private val binding get() = _binding!!
    // TODO: safe args class property
    private lateinit var firestore: FirebaseFirestore
    private val args:ItineraryDetailFragmentArgs by navArgs()

      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          // binding
          _binding = FragmentItineraryDetailBinding.inflate(inflater, container, false)
          val view = binding.root
          return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        val tvParkName = view.findViewById<TextView>(R.id.tvParkName)
        val tvAdress = view.findViewById<TextView>(R.id.tvAdress)
        val tvTripDate = view.findViewById<TextView>(R.id.tvTripDate)
        val tvNotes = view.findViewById<TextView>(R.id.tvNotes)
        val btDelete = view.findViewById<TextView>(R.id.btDelete)
        tvParkName.setText("${args.parkName}")
        tvAdress.setText("Address : ${args.parkAddress}")
        tvTripDate.setText("${args.parkTripDate}")
        tvNotes.setText("${args.parkNote}")
        btDelete.setOnClickListener {
            firestore.collection("itinerary").document("${args.parkID}").delete()
            Toast.makeText(activity,"Delete Itinerary Successful!",Toast.LENGTH_LONG).show();
            findNavController().popBackStack()
        }
   }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
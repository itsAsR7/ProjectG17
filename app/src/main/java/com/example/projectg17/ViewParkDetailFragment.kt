package com.example.projectg17

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.projectg17.databinding.FragmentFindParkBinding
import com.example.projectg17.databinding.FragmentViewParkDetailBinding
import com.example.projectg17.models.ParkByStateReponseObject
import com.example.projectg17.models.ParkData
import com.example.projectg17.models.ParksObj
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class ViewParkDetailFragment : Fragment(R.layout.fragment_itinerary) {

    // binding variables
    private var _binding: FragmentViewParkDetailBinding? = null
    private val binding get() = _binding!!

    // TODO: safe args class property
    private val args:ViewParkDetailFragmentArgs by navArgs()

      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          // binding
          _binding = FragmentViewParkDetailBinding.inflate(inflater, container, false)
          val view = binding.root
          return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvParkName = view.findViewById<TextView>(R.id.tvParkName)
        val tvAdress = view.findViewById<TextView>(R.id.tvAdress)
        val tvWebsite = view.findViewById<TextView>(R.id.tvWebsite)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

        tvParkName.setText("${args.parkName}")
        tvAdress.setText("${args.parkAddress}")
        tvWebsite.setText("${args.parkURL}")
        tvDescription.setText("${args.parkDescription}")

        tvWebsite.setMovementMethod(LinkMovementMethod.getInstance())

        Glide.with(requireContext())
            .load(args.parkImage)
            .into(binding.ivPhoto)
   }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
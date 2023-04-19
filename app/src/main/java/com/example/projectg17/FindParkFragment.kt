package com.example.projectg17

class FindParkFragment : Fragment(R.layout.fragment_find_park) {

    private lateinit var binding: FragmentFindParkBinding
    private lateinit var viewModel: FindParkViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFindParkBinding.bind(view)
        viewModel = ViewModelProvider(this).get(FindParkViewModel::class.java)

        setupViews()
    }

    private fun setupViews() {
        val states = resources.getStringArray(R.array.states)
        val stateAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, states)
        binding.spinnerStates.adapter = stateAdapter

        binding.btnFindParks.setOnClickListener {
            val selectedState = binding.spinnerStates.selectedItem.toString()
            viewModel.findParks(selectedState)
        }

        viewModel.parks.observe(viewLifecycleOwner) { parks ->
            binding.mapView.getMapAsync { googleMap ->
                googleMap.clear()
                parks.forEach { park ->
                    val marker = googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(park.latitude, park.longitude))
                            .title(park.name)
                    )
                    marker.tag = park
                }
                googleMap.setOnMarkerClickListener { marker ->
                    val park = marker.tag as Park
                    viewModel.onParkSelected(park)
                    true
                }
            }
        }

        viewModel.navigateToParkDetails.observe(viewLifecycleOwner) { park ->
            if (park != null) {
                val action = FindParkFragmentDirections.actionFindParkFragmentToViewParkFragment(park)
                findNavController().navigate(action)
                viewModel.onParkDetailsNavigated()
            }
        }
    }
}

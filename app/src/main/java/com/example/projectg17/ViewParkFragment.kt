package com.example.projectg17

class ViewParkFragment : Fragment(R.layout.fragment_view_park) {

    private lateinit var binding: FragmentViewParkBinding
    private lateinit var viewModel: ViewParkViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentViewParkBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ViewParkViewModel::class.java)

        setupViews()
    }

    private fun setupViews() {
        val park = ViewParkFragmentArgs.fromBundle(requireArguments()).park
        binding.parkName.text = park.name
        binding.parkAddress.text = park.address

        Glide.with(requireContext())
            .load(park.imageUrl)
            .into(binding.parkImage)

        binding.parkWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(park.websiteUrl))
            startActivity(intent)
        }

        binding.btnAddToItinerary.setOnClickListener {
            viewModel.addToItinerary(park)
            Toast.makeText(requireContext(), "Added to itinerary", Toast.LENGTH_SHORT).show()
        }
    }
}

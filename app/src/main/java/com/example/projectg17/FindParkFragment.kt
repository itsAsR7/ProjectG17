package com.example.projectg17

//import retrofit2.Response

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.projectg17.databinding.FragmentFindParkBinding
import com.example.projectg17.models.*
import com.example.projectg17.networking.ApiService
import com.example.projectg17.networking.RetrofitInstance
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import retrofit2.Response

class FindParkFragment : Fragment(R.layout.fragment_find_park), OnMapReadyCallback {

    private val TAG="SCREEN1"

    // binding variables
    private var _binding: FragmentFindParkBinding? = null
    private val binding get() = _binding!!

    // list of locations
    private val parkLocations:MutableList<LatLng> = mutableListOf()
    private lateinit var myArrayAdapterSpinner:ArrayAdapter<String>
    // map variable
    private lateinit var mMap: GoogleMap
    var listOfParks:MutableList<ParksObj> = mutableListOf<ParksObj>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding
        _binding = FragmentFindParkBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Setup the map
        val mapFragment =  childFragmentManager.findFragmentById(binding.fragmentMap.id) as? SupportMapFragment

        // debugging messages to help determine if the screen can find the map

        if (mapFragment == null) {
            Log.d(TAG, "++++ map fragment is null")
        }
        else {
            Log.d(TAG, "++++ map fragment is NOT null")
            // assuming the screen can find the map fragment in the xml file, then
            // connect with Google and get whatever information you need from Google
            // to setup the map
            mapFragment?.getMapAsync(this)
        }

        val categorySpinner = view.findViewById<Spinner>(R.id.stateSpinner)
        val stateName = arrayOf(
            "Alabama", "Alaska", "Arizona", "Arkansas", "California","Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
            "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa","Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
        )
        val stateUSPS = arrayOf(
            "AL", "AK", "AZ", "AR", "CA","CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA","KS", "KY", "LA", "ME", "MD",
        )

        this.myArrayAdapterSpinner = ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_item,stateName)
        myArrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = myArrayAdapterSpinner

        val btFindPark = view.findViewById<TextView>(R.id.btFindPark)
        btFindPark.setOnClickListener {
            mMap.clear()
            val parkSelected: String = categorySpinner.selectedItem.toString()
            for (i in 1..stateName.size){
                if (stateName[i-1] == parkSelected) {
                    println("parkName : ${stateName[i-1]}")
                    lifecycleScope.launch {
                        var responseFromAPI:ParkByStateReponseObject? =
                            getRandomUsersFromAPI(stateUSPS[i-1])
                        if (responseFromAPI == null) {
                            return@launch
                        }
                        // TODO: get the data from the array

                        val parkList:List<ParkData> = responseFromAPI.data
                        parkLocations.clear()
                        listOfParks.clear()
                        for (currPark:ParkData in parkList) {
                            val lat:Double = currPark.latitude.toDouble()
                            val lng:Double = currPark.longitude.toDouble()
                            parkLocations.add(LatLng(lat, lng))
                            mMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(currPark.fullName))
                            mMap.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener { arg0 ->
                                val parkname = arg0.title.toString()

                                for (currPark:ParkData in parkList) {
                                    if (currPark.fullName == parkname){
                                        val action = FindParkFragmentDirections.actionFindParkFragmentToDetailParkFragment(
                                            currPark.id,
                                            currPark.fullName,
                                            "${currPark.addresses[0].line2}, ${currPark.addresses[0].city}, ${currPark.addresses[0].stateCode}",
                                            currPark.url,
                                            currPark.description,
                                            currPark.images[0].url,
                                        )
                                        findNavController().navigate(action)
                                    }
                                }
                            })
                            var park = ParksObj(currPark.fullName,currPark.description)
                            listOfParks.add(park)
                        }
                        val intialLocation = LatLng(parkLocations[0].latitude, parkLocations[0].longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(intialLocation, 3.0f))
                    }
                }
            }
        }

        // TODO: get data from API
//        lifecycleScope.launch {
//            var responseFromAPI:ParkByStateReponseObject? =
//                getRandomUsersFromAPI(stateUSPS[0])
//            if (responseFromAPI == null) {
//                return@launch
//            }
//
//            // TODO: get the data from the array
//
//            val parkList:List<ParkData> = responseFromAPI.data
//            parkLocations.clear()
//            listOfParks.clear()
//            for (currPark:ParkData in parkList) {
//
//                val lat:Double = currPark.latitude.toDouble()
//                val lng:Double = currPark.longitude.toDouble()
//
//                parkLocations.add(LatLng(lat, lng))
//                mMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(currPark.fullName))
//                mMap.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener { arg0 ->
//                    val parkname = arg0.title.toString()
//                    val action = FindParkFragmentDirections.actionFindParkFragmentToDetailParkFragment(
//                        parkname,
//                    )
//                    findNavController().navigate(action)
//                })
//
//                var park = ParksObj(currPark.fullName,currPark.description)
//                listOfParks.add(park)
//            }
//            val intialLocation = LatLng(parkLocations[0].latitude, parkLocations[0].longitude)
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(intialLocation, 4.0f))
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // TODO: helper functions for api
    private suspend fun getRandomUsersFromAPI(state : String): ParkByStateReponseObject? {
        var apiService: ApiService = RetrofitInstance.retrofitService
        val response: Response<ParkByStateReponseObject> = apiService.getParkList(state,"GUqfZFKxWaxXbbBTLaK5vY58aFbdubWMTnhNDqb0")
        if (response.isSuccessful) {
            val dataFromAPI = response.body()   /// myresponseobject
            if (dataFromAPI == null) {
                Log.d("API", "No data from API or some other error")
                return null
            }
            // if you reach this point, then you must have received a response from the api
            Log.d(TAG, "Here is the data from the API")
            Log.d(TAG, dataFromAPI.toString())
            return dataFromAPI
        }
        else {
            // Handle error
            Log.d(TAG, "An error occurred")
            return null
        }
    }

    // TODO: Google Map callback function
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(TAG, "+++ Map callback is executing...")
        // initialize the map
        this.mMap = googleMap
        // configure the map's options
        // - set the map type (hybrid, satellite, etc)
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        // - select if traffic data should be displayed
        mMap.isTrafficEnabled = true
        // - add user interface controls to the map (zoom, compass, etc)
        val uiSettings = googleMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isCompassEnabled = true
        Log.d(TAG, "+++ Map is finished loading!...")

    }

}
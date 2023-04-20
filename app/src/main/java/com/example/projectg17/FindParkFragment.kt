package com.example.projectg17

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
import com.example.projectg17.models.PersonData
import com.example.projectg17.models.RandomUserReponseObject
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
//import retrofit2.Response
import java.util.Random

class FindParkFragment : Fragment(R.layout.fragment_find_park), OnMapReadyCallback {

    private val TAG="SCREEN1"

    // binding variables
    private var _binding: FragmentFindParkBinding? = null
    private val binding get() = _binding!!

    // TODO: data source for adapter
    private val usernamesList:MutableList<String> = mutableListOf("Name 1", "Name 2", "Name 3")
    private lateinit var myArrayAdapter: ArrayAdapter<String>

    // list of locations
    private val userLocations:MutableList<LatLng> = mutableListOf()

    // map variable
    private lateinit var mMap: GoogleMap

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
        Log.d(TAG, "3- fragment onViewCreated() running")

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

        // TODO: get data from API
        lifecycleScope.launch {
            var responseFromAPI:RandomUserReponseObject? =
                getRandomUsersFromAPI()
            if (responseFromAPI == null) {
                return@launch
            }
            Log.d(TAG, "Success: Data retrieved from API")

            // TODO: get the data from the array

            // 1. Get the 'results' attribute from the RandomUserResponseObject
            val personList:List<PersonData> = responseFromAPI.results

            // - Loop through the list of people, and get their coordinates
            for (currPerson:PersonData in personList) {
                Log.d(TAG, "Name: ${currPerson.name.first} ${currPerson.name.last}")

                Log.d(TAG, "Lat: ${currPerson.location.coordinates.latitude}")
                Log.d(TAG, "Lng: ${currPerson.location.coordinates.longitude}")

                val lat:Double = currPerson.location.coordinates.latitude.toDouble()
                val lng:Double = currPerson.location.coordinates.longitude.toDouble()

                // build a LatLng for the person and save it to an array
                userLocations.add(LatLng(lat, lng))
            }

            Log.d(TAG, "Done with our data, # of coordinates found are: ${userLocations.size}")

            for (coord in userLocations) {
                mMap.addMarker(MarkerOptions().position(coord).title("Hello"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // TODO: helper functions for api
    private suspend fun getRandomUsersFromAPI(): RandomUserReponseObject? {
        var apiService: ApiService = RetrofitInstance.retrofitService
        val response: Response<RandomUserReponseObject> = apiService.getRandomUsers()

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

        val intialLocation = LatLng(43.6426, -79.3871)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(intialLocation, 2.0f))
        mMap.addMarker(MarkerOptions().position(intialLocation).title("Welcome to Toronto"))

        Log.d(TAG, "+++ Map is finished loading!...")

        // after the map is ready, get the markers and output to the screen

    }

}
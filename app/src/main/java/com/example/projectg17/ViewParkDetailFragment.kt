package com.example.projectg17

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment

class ViewParkDetailFragment : Fragment(R.layout.fragment_itinerary) {
      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Required for displaing the menu
        // You can ignore the deprecation warnings
        // But if the deprecation warnings bother you, you can use this code to get rid of the deprecation warnings:
        // https://stackoverflow.com/a/71965674
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // 1. Add the onCreateOptionsMenu
    // - Notice the function signature is slightly different for a Fragment vs Activity
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val inflater: MenuInflater = inflater
        inflater.inflate(R.menu.options_menu, menu)
    }

    // 2. Add the onOptionsItemSelected function
    // - The function signature is the same in a Fragment as in an Activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
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

}
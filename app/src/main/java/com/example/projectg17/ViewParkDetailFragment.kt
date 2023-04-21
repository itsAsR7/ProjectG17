package com.example.projectg17

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.projectg17.databinding.FragmentViewParkDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ViewParkDetailFragment : Fragment(R.layout.fragment_itinerary) {

    // binding variables
    private var _binding: FragmentViewParkDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
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

        firestore = FirebaseFirestore.getInstance()



        val tvParkName = view.findViewById<TextView>(R.id.tvParkName)
        val tvAdress = view.findViewById<TextView>(R.id.tvAdress)
        val tvWebsite = view.findViewById<TextView>(R.id.tvWebsite)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val btAddToItinerary = view.findViewById<TextView>(R.id.btAdd)
        btAddToItinerary.setOnClickListener {
            showDialogOne()
        }
        tvParkName.setText("${args.parkName}")
        tvAdress.setText("${args.parkAddress}")
        tvWebsite.setText("${args.parkURL}")
        tvDescription.setText("${args.parkDescription}")
        tvWebsite.setMovementMethod(LinkMovementMethod.getInstance())
        Glide.with(requireContext())
            .load(args.parkImage)
            .into(binding.ivPhoto)
   }

    private suspend fun createDocument(parkName : String, Address : String, Date : String, Note : String) {
        val documentData = hashMapOf(
            "name" to parkName,
            "address" to Address,
            "dateofvisit" to Date,
            "notes" to Note,
        )
        try {
            firestore.collection("itinerary").document("itinerary-${args.parkID}")
                .set(documentData)
                .await()
            Toast.makeText(activity,"Create Itinerary Successful!",Toast.LENGTH_LONG).show();
        } catch (e: Exception) {
            println("Error creating document: $e")
        }
    }

    fun showDialogOne() {

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.sample_dialog)
        val etDate= dialog.findViewById<EditText>(R.id.etDate)
        val etNotes= dialog.findViewById<EditText>(R.id.etNotes)
        val btSave= dialog.findViewById<Button>(R.id.btSave)

        btSave?.setOnClickListener {
            if (etDate?.text?.trim().toString() != ""){
                CoroutineScope(Dispatchers.IO).launch {
                    createDocument(args.parkName, args.parkAddress, etDate?.text.toString(), etNotes?.text.toString())
                }
                dialog.dismiss()
            }else{
                dialog.dismiss()
                return@setOnClickListener
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
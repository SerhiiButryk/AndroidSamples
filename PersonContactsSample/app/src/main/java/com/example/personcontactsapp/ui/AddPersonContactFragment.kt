package com.example.personcontactsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.personcontactsapp.R
import com.example.personcontactsapp.data.PersonContact
import com.example.personcontactsapp.state_holders.PersonContactViewModel

class AddPersonContactFragment : Fragment() {

    private val TAG: String = AddPersonContactFragment::class.java.simpleName

    // View Model is scoped to a navigation graph
    private val viewModel: PersonContactViewModel by navGraphViewModels(R.id.main_nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: fragment is created")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_person_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup button click listener
        // It will ask View Model to save entered values
        view.findViewById<Button>(R.id.enterBtn).setOnClickListener {

            val nameField = view.findViewById<EditText>(R.id.editTextTextPersonName)
            val ageField = view.findViewById<EditText>(R.id.editTextTextPersonAge)

            if (nameField.text.isNotEmpty() && ageField.text.isNotEmpty()) {

                val name = nameField.text.toString().trim()
                val age = ageField.text.toString().trim().toInt()

                viewModel.setPersonData(PersonContact(name, age))
            }

            // Open DetailsContactFragment
            val detailsContactFragment = AddPersonContactFragmentDirections
                .actionPersonContactFragmentToDetailsContactFragment()

            findNavController().navigate(detailsContactFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "init: fragment is destroyed")
    }
}
package com.example.personcontactsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.navGraphViewModels
import com.example.personcontactsapp.R
import com.example.personcontactsapp.state_holders.PersonContactViewModel

class DetailsContactFragment : Fragment() {

    private val TAG: String = DetailsContactFragment::class.java.simpleName

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
        return inflater.inflate(R.layout.fragment_details_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView = view.findViewById<TextView>(R.id.name_tv)
        val ageTextView = view.findViewById<TextView>(R.id.age_tv)

        val personData = viewModel.getPersonData()

        if (personData.hasData()) {
            val nameString = getString(R.string.name_string)
            nameTextView.text = nameString.format(personData.name)

            val ageString = getString(R.string.age_string)
            ageTextView.text = ageString.format(personData.age)
        } else {
            nameTextView.text = getString(R.string.default_string)
            ageTextView.text = getString(R.string.default_string)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "init: fragment is destroyed")
    }
}
package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "FragmentScreenTwo"

/**
 * Application screen 1
 *
 * Demonstrates navigation to Fragment screen 2
 */
class FragmentScreenOne : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate() in")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            Log.i(TAG, "onCreate() param1 = $param1")
            param2 = it.getString(ARG_PARAM2)
            Log.i(TAG, "onCreate() param2 = $param2")
        }
        Log.i(TAG, "onCreate() out")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated() in")
        val goToScreenTwoBtn = view.findViewById<Button>(R.id.go_to_screen_two)
        // Navigation to screen 2
        goToScreenTwoBtn.setOnClickListener {
            // Solution 1
            // Specify action id
//             findNavController().navigate(R.id.action_screenOne_to_screenTwo)

            // Solution 2
            // Specify direction id
//             findNavController().navigate(R.id.screenTwo)

            // Solution 3
            // Using safeArgs
            val screenTwoDirection = FragmentScreenOneDirections.actionScreenOneToScreenTwo("arg1", "arg2")
            findNavController().navigate(screenTwoDirection, )

            // Also you can create a DeepLink dynamically here
//            findNavController()
//                .createDeepLink()
//                .setDestination("")
//                .setArguments(Bundle())

            // Also you can pass flags to Activity
//            val extras = ActivityNavigator.Extras.Builder()
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                .build()
//            findNavController().navigate(BlaBlaActivityDirections.actionFooBarToBlaBlaActivity(), extras)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView() in")
        return inflater.inflate(R.layout.screen_one, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FragmentScreenOne.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String): Fragment {
            Log.i(TAG, "newInstance() param2 = $param1 param1 = $param2")
            val fragment = FragmentScreenOne().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
            return fragment;
        }

    }
}
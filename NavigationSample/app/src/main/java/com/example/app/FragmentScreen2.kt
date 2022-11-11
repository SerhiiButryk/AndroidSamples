package com.example.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val TAG = "FragmentScreenTwo"

/**
 * Application screen 2
 */
class FragmentScreenTwo : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView() in")
        return inflater.inflate(R.layout.screen_two, container, false)
    }

    companion object {

        const val ARG_PARAM1 = "param1"
        const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FragmentScreenTwo.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String): Fragment {
            Log.i(TAG, "newInstance() param2 = $param1 param1 = $param2")
            val fragment = FragmentScreenTwo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
            return fragment;
        }

    }
}
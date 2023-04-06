package com.example.personcontactsapp.state_holders

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personcontactsapp.data.PersonContact

class PersonContactViewModel : ViewModel() {

    private val TAG: String = PersonContactViewModel::class.java.simpleName

    private val personData: MutableLiveData<PersonContact> = MutableLiveData()

    init {
        Log.i(TAG, "init: View Model is created")
    }

    fun setPersonData(newData: PersonContact) {
        personData.value = newData
        Log.i(TAG, "setPersonData: data is set")
    }

    fun getPersonData(): PersonContact {
        if (personData.value == null) {
            return PersonContact()
        }
        return personData.value!!
    }

    override fun onCleared() {
        Log.i(TAG, "onCleared: View Model is cleared")
    }
}
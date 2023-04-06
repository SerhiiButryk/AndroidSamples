package com.example.personcontactsapp.data

data class PersonContact(val name: String, val age: Int) {

    // Default constructor
    constructor() : this("", 0) {}

    fun hasData() = name.isNotEmpty() && age != 0
}

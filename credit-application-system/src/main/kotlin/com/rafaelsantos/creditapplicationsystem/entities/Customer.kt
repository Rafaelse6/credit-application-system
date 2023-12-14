package com.rafaelsantos.creditapplicationsystem.entities

data class Customer(
    val id: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    val cpf: String,
    var email: String = "",
    val password: String = "",
    var address: Address = Address(),
    var credits: List<Credit> = mutableListOf(),
)

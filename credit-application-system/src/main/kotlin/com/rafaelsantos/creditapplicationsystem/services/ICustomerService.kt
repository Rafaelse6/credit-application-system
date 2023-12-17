package com.rafaelsantos.creditapplicationsystem.services

import com.rafaelsantos.creditapplicationsystem.entities.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer

    fun findById(id: Long): Customer

    fun delete(id: Long)
}
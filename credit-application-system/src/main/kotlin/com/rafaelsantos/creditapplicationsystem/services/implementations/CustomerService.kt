package com.rafaelsantos.creditapplicationsystem.services.implementations

import com.rafaelsantos.creditapplicationsystem.entities.Customer
import com.rafaelsantos.creditapplicationsystem.exceptions.BusinessException
import com.rafaelsantos.creditapplicationsystem.repositories.CustomerRepository
import com.rafaelsantos.creditapplicationsystem.services.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) : ICustomerService{

    override fun save(customer: Customer): Customer {
        return this.customerRepository.save(customer)
    }

    override fun findById(id: Long): Customer {
        return this.customerRepository.findById(id).orElseThrow {
            throw BusinessException("Id $id not found!")
        }
    }

    override fun delete(id: Long) {
        val customer: Customer = this.findById(id)
        this.customerRepository.delete(customer)
    }
}
package com.rafaelsantos.creditapplicationsystem.controllers

import com.rafaelsantos.creditapplicationsystem.dto.CustomerDTO
import com.rafaelsantos.creditapplicationsystem.dto.CustomerUpdateDTO
import com.rafaelsantos.creditapplicationsystem.dto.CustomerView
import com.rafaelsantos.creditapplicationsystem.entities.Customer
import com.rafaelsantos.creditapplicationsystem.services.implementations.CustomerService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerResource (
    private val customerService: CustomerService
){

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): String{
        val savedCustomer = this.customerService.save(customerDTO.toEntity())
        return "Customer ${savedCustomer.email} saved"
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) : CustomerView {
        val customer: Customer = this.customerService.findById(id)
        return CustomerView(customer)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) = this.customerService.delete(id)

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId") id: Long,
                       @RequestBody customerUpdateDTO: CustomerUpdateDTO) : CustomerView{
        val customer: Customer = this.customerService.findById(id)

        val customerToUpdate: Customer = customerUpdateDTO.toEntity(customer)
        val customerUpdated : Customer = this.customerService.save(customerToUpdate)
        return CustomerView(customerUpdated)
    }
}
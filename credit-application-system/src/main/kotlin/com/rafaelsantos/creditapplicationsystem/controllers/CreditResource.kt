package com.rafaelsantos.creditapplicationsystem.controllers

import com.rafaelsantos.creditapplicationsystem.dto.CreditDTO
import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.services.implementations.CreditService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService
) {

    @PostMapping
    fun saveCredit(@RequestBody creditDTO: CreditDTO) : String{
        val credit : Credit = this.creditService.save(creditDTO.toEntity())
        return "Credit ${credit.creditCode} - Customer ${credit.customer?.firstName} saved !"
    }
}
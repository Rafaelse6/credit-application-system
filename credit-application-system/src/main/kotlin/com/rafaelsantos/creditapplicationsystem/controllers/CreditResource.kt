package com.rafaelsantos.creditapplicationsystem.controllers

import com.rafaelsantos.creditapplicationsystem.dto.CreditDTO
import com.rafaelsantos.creditapplicationsystem.dto.CreditViewList
import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.services.implementations.CreditService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

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

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId : Long) : List<CreditViewList>{
        return this.creditService.findAllByCustomer(customerId).stream()
            .map { credit : Credit -> CreditViewList(credit) }
            .collect(Collectors.toList())
    }
}
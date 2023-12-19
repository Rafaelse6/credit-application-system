package com.rafaelsantos.creditapplicationsystem.dto

import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.entities.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreditDTO (
    val creditValue: BigDecimal,
    val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,
    val customerId: Long
){

    fun toEntity() : Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
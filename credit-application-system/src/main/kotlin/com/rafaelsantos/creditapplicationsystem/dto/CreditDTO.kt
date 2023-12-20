package com.rafaelsantos.creditapplicationsystem.dto

import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.entities.Customer
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreditDTO (
    @field:NotNull(message = "Invalid input")
    val creditValue: BigDecimal,

    @field:Future
    val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,

    @field:NotNull(message = "Invalid input")
    val customerId: Long
){

    fun toEntity() : Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
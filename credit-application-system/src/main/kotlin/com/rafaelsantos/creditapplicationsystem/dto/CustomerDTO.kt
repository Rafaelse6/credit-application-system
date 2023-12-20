package com.rafaelsantos.creditapplicationsystem.dto

import com.rafaelsantos.creditapplicationsystem.entities.Address
import com.rafaelsantos.creditapplicationsystem.entities.Customer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDTO(

    @field:NotEmpty(message = "Invalid input")
    val firstName: String,

    @field:NotEmpty(message = "Invalid input")
    val lastName: String,

    @field:CPF(message = "Invalic CPF")
    @field:NotEmpty(message = "Invalid input")
    val cpf: String,

    @field:NotNull(message = "Invalid input")
    val income: BigDecimal,

    @field:Email(message = "Invalid Email")
    @field:NotEmpty(message = "Invalid input")
    val email: String,

    @field:NotEmpty(message = "Invalid input")
    val password: String,

    @field:NotEmpty(message = "Invalid input")
    val zipCode: String,

    @field:NotEmpty(message = "Invalid input")
    val street: String
) {

    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}
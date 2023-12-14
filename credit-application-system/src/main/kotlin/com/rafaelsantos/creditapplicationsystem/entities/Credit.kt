package com.rafaelsantos.creditapplicationsystem.entities

import com.rafaelsantos.creditapplicationsystem.enums.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Credit(
    val id: Long? = null,
    val creditCode: UUID = UUID.randomUUID(),
    val creditValue: BigDecimal = BigDecimal.ZERO,
    val dayFirstInstallment: LocalDate,
    val numberOfInstallElements: Int = 0,
    val status: Status = Status.IN_PROGRESS,
    val customer: Customer? = null
)

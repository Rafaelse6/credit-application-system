package com.rafaelsantos.creditapplicationsystem.service

import com.rafaelsantos.creditapplicationsystem.entities.Address
import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.entities.Customer
import com.rafaelsantos.creditapplicationsystem.repositories.CreditRepository
import com.rafaelsantos.creditapplicationsystem.services.implementations.CreditService
import com.rafaelsantos.creditapplicationsystem.services.implementations.CustomerService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService


   @Test
   fun `should create credit`(){

       //given
       val credit:Credit = buildCredit()
       val customerId: Long = 1

       every { customerService.findById(customerId) } returns credit.customer!!
       every { creditRepository.save(credit) } returns credit

       //when
       val actual : Credit = creditService.save(credit)

       //then
       verify { customerService.findById(customerId) }
       verify { creditRepository.save(credit) }

       Assertions.assertThat(actual).isNotNull
       Assertions.assertThat(actual).isSameAs(credit)
   }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(100.0),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2L),
        numberOfInstallments: Int = 15,
        customer: Customer = buildCustomer()
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    private fun buildCustomer(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua da Cami",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        id: Long = 1L
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
        id = id
    )
}
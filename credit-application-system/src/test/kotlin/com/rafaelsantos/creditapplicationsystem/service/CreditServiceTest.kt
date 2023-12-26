package com.rafaelsantos.creditapplicationsystem.service

import com.rafaelsantos.creditapplicationsystem.entities.Address
import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.entities.Customer
import com.rafaelsantos.creditapplicationsystem.exceptions.BusinessException
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
import java.util.UUID
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

   @Test
   fun `should not save credit when invalid data`(){
       //given
       val invalidDayOfInstallment: LocalDate = LocalDate.now().plusMonths(7)
       val credit: Credit = buildCredit(dayFirstInstallment = invalidDayOfInstallment)

       every { creditRepository.save(credit) } answers { credit }

       //when
       Assertions.assertThatThrownBy { creditService.save(credit) }
           .isInstanceOf(BusinessException::class.java)
           .hasMessage("Invalid Date")

       //then
       verify(exactly = 0) { creditRepository.save(any()) }
   }

   @Test
   fun `should find all credits by customer`(){
       //given
       val customerId: Long = 1
       val listOfCredits : List<Credit> = listOf(buildCredit(), buildCredit(), buildCredit())

       every { creditRepository.findAllByCustomerId(customerId) } returns listOfCredits

       //when
       val actual : List<Credit> = creditService.findAllByCustomer(customerId)

       //then
       Assertions.assertThat(actual).isNotNull
       Assertions.assertThat(actual).isNotEmpty
       Assertions.assertThat(actual).isSameAs(listOfCredits)

       verify(exactly = 1) { creditRepository.findAllByCustomerId(customerId) }
   }

    @Test
    fun `should return credit when valid customer and valid credit`(){
        //given
        val customerId: Long = 1
        val creditCode: UUID = UUID.randomUUID()
        val credit: Credit = buildCredit(customer = Customer(id = customerId))

        every { creditRepository.findByCreditCode(creditCode) } returns credit

        //when
        val actual: Credit = creditService.findByCreditCode(customerId, creditCode)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(credit)

        verify(exactly = 1) { creditRepository.findByCreditCode(creditCode) }
    }

    @Test
    fun `should throw Business exception for invalid credit code`(){
        //given
        val customerId: Long = 1
        val invalidCreditCode: UUID = UUID.randomUUID()

        every { creditRepository.findByCreditCode(invalidCreditCode) } returns null

        //when && then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, invalidCreditCode) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Credit $invalidCreditCode not found !")

        verify(exactly = 1) { creditRepository.findByCreditCode(invalidCreditCode) }
    }

    @Test
    fun `should throw IllegalArgumentException for different customer ID`(){
        //given
        val customerId: Long = 1
        val creditCode: UUID = UUID.randomUUID()
        val credit: Credit = buildCredit(customer = Customer(id = 2L))

        every { creditRepository.findByCreditCode(creditCode) } returns credit

        //when && then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, creditCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Contact admin")

        verify { creditRepository.findByCreditCode(creditCode) }
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
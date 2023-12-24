package com.rafaelsantos.creditapplicationsystem.services.implementations

import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.exceptions.BusinessException
import com.rafaelsantos.creditapplicationsystem.repositories.CreditRepository
import com.rafaelsantos.creditapplicationsystem.services.ICreditService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
) : ICreditService {
    override fun save(credit: Credit): Credit {
        this.validateFirstDayOfInstallment(credit.dayFirstInstallment)
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }

        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomerId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
       val credit: Credit =
           (this.creditRepository.findByCreditCode(creditCode)
               ?: throw BusinessException("Credit $creditCode not found !"))
        return if(credit.customer?.id == customerId) credit
        else throw IllegalArgumentException("Contact admin")
    }

    private fun validateFirstDayOfInstallment(firstDayOfInstallment: LocalDate) : Boolean{
        return if (firstDayOfInstallment.isBefore(LocalDate.now().plusMonths(3))) true
        else throw BusinessException("Invalid Date")
    }

}
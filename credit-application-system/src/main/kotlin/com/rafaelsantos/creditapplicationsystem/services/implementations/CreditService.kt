package com.rafaelsantos.creditapplicationsystem.services.implementations

import com.rafaelsantos.creditapplicationsystem.entities.Credit
import com.rafaelsantos.creditapplicationsystem.repositories.CreditRepository
import com.rafaelsantos.creditapplicationsystem.services.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
) : ICreditService {
    override fun save(credit: Credit): Credit {
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
               ?: throw RuntimeException("Credit $creditCode not found !"))
        return if(credit.customer?.id == customerId) credit else throw RuntimeException("Contact admin")
    }

}
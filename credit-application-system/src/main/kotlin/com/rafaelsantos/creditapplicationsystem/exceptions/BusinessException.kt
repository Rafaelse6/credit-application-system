package com.rafaelsantos.creditapplicationsystem.exceptions

data class BusinessException (override val message: String?) : RuntimeException(message)
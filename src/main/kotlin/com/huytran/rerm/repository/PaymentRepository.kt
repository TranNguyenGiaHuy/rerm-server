package com.huytran.rerm.repository

import com.huytran.rerm.model.Payment
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaymentRepository : RepositoryCore<Payment, Long> {

    fun findTopByContract_IdOrderByTsEndDesc(roomId: Long): Optional<Payment>

    fun findAllByContract_IdAndStatusNot(contractId: Long, status: Int): List<Payment>

}
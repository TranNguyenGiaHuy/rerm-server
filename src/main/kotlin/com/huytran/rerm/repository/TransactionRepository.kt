package com.huytran.rerm.repository

import com.huytran.rerm.model.Transaction
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Deprecated("Not Use")
@Repository
interface TransactionRepository : RepositoryCore<Transaction, Long> {

}
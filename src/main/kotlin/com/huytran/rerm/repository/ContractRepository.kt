package com.huytran.rerm.repository

import com.huytran.rerm.model.Contract
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository : RepositoryCore<Contract, Long> {

}
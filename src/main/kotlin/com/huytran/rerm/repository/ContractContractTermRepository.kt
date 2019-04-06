package com.huytran.rerm.repository

import com.huytran.rerm.model.ContractContractTerm
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractContractTermRepository : RepositoryCore<ContractContractTerm, Long> {

}
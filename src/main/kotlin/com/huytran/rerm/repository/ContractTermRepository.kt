package com.huytran.rerm.repository

import com.huytran.rerm.model.ContractTerm
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractTermRepository : RepositoryCore<ContractTerm, Long> {

}
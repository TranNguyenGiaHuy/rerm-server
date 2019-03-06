package com.huytran.rerm.repository

import com.huytran.rerm.model.ContractContractTerm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractContractTermRepository : JpaRepository<ContractContractTerm, Long> {

}
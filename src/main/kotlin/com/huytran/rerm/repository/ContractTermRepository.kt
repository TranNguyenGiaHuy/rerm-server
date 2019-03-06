package com.huytran.rerm.repository

import com.huytran.rerm.model.ContractTerm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractTermRepository : JpaRepository<ContractTerm, Long> {

}
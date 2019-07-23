package com.huytran.rerm.repository

import com.huytran.rerm.model.Contract
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ContractRepository : RepositoryCore<Contract, Long> {

    fun findAllByAvailable(available: Boolean) : List<Contract>

    fun findByRoom_IdAndAvailableOrderByTsLastModifiedDesc(roomId: Long, available: Boolean): Optional<Contract>

    fun findAllByRenter_IdOrOwner_Id(renterId: Long, ownerId: Long): List<Contract>
}
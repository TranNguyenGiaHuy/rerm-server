package com.huytran.rerm.repository

import com.huytran.rerm.model.RentRequest
import com.huytran.rerm.model.Room
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.stereotype.Repository

@Repository
interface RentRequestRepository : RepositoryCore<RentRequest, Long> {

    fun findByRoomAndAvailable(room: Room, available: Boolean) : List<RentRequest>

}
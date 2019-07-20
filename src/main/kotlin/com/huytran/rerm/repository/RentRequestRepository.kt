package com.huytran.rerm.repository

import com.huytran.rerm.model.RentRequest
import com.huytran.rerm.model.Room
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RentRequestRepository : RepositoryCore<RentRequest, Long> {

    fun findByRoomAndAvailable(room: Room, available: Boolean) : List<RentRequest>

    fun findByRenter_IdAndRoom_IdAndAvailable(renterId: Long, roomId: Long, available: Boolean): Optional<RentRequest>
}
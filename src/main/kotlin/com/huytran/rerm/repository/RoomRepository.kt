package com.huytran.rerm.repository

import com.huytran.rerm.model.Room
import com.huytran.rerm.model.User
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomRepository : RepositoryCore<Room, Long> {
    fun findByOwner_IdAndAvailable(userId: Long, available: Boolean): List<Room>

    fun findByOwner_Id(userId: Long): List<Room>

    fun findByIdAndAvailableAndRenting(id: Long, available: Boolean, renting:  Boolean): Optional<Room>

    fun findAllByTitleContainingOrAddressContainingOrDescriptionContaining(title: String, address: String, description: String): List<Room>
}
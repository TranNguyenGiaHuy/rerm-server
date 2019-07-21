package com.huytran.rerm.repository

import com.huytran.rerm.model.Room
import com.huytran.rerm.model.SavedRoom
import com.huytran.rerm.model.User
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SavedRoomRepository : RepositoryCore<SavedRoom, Long> {

    fun findByAvailableAndUser(available: Boolean, user: User) : List<SavedRoom>

    fun findByRoomAndUser(room: Room, user: User) : Optional<SavedRoom>

    fun findByUser_IdAndRoom_IdAndAvailable(userId: Long, roomId: Long, available: Boolean): Optional<SavedRoom>

}
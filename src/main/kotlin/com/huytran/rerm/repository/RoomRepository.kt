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

}
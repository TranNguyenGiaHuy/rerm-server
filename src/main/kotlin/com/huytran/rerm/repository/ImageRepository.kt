package com.huytran.rerm.repository

import com.huytran.rerm.model.Image
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : RepositoryCore<Image, Long> {
    fun findByRoomId(roomId: Long): List<Image>
}
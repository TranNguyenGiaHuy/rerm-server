package com.huytran.rerm.repository

import com.huytran.rerm.model.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Long> {
    fun findByRoomId(roomId: Long): List<Image>
}
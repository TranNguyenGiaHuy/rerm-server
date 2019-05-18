package com.huytran.rerm.repository

import com.huytran.rerm.model.GrpcSession
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GrpcSessionRepository : RepositoryCore<GrpcSession, Long> {
    fun findByToken(token: String): Optional<GrpcSession>

    fun findByUser_IdAndAvailable(userId: Long, available: Boolean): List<GrpcSession>
}
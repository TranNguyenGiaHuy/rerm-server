package com.huytran.rerm.repository

import com.huytran.rerm.model.GrpcSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GrpcSessionRepository : JpaRepository<GrpcSession, Long> {
    fun findByToken(token: String): Optional<GrpcSession>
}
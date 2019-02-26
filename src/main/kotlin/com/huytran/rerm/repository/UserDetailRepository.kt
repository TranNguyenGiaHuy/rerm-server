package com.huytran.rerm.repository

import com.huytran.rerm.model.UserDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserDetailRepository : JpaRepository<UserDetail, Long> {

    fun findByUser_Id(userId: Long) : Optional<UserDetail>

    override fun findAll(pageable: Pageable) : Page<UserDetail>

}
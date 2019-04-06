package com.huytran.rerm.repository

import com.huytran.rerm.model.User
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : RepositoryCore<User, Long> {

    fun findByName(name: String): Optional<User>

    @Query(nativeQuery = true,
            value = "select u.id, u.name as username, ud.name as name, ud.address, ud.phone_number from user u, user_detail ud where u.id = ud.user_id")
    fun getNativeDetail(): List<Any>

}
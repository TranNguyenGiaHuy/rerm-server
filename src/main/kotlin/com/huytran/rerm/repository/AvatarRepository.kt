package com.huytran.rerm.repository

import com.huytran.rerm.model.Avatar
import com.huytran.rerm.model.User
import com.huytran.rerm.repository.core.RepositoryCore
import org.springframework.stereotype.Repository

@Repository
interface AvatarRepository : RepositoryCore<Avatar, Long> {

    fun findByAvailableAndUser(available: Boolean, user: User) : List<Avatar>

}
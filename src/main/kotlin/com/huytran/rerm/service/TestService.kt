package com.huytran.rerm.service

import com.huytran.rerm.model.User
import com.huytran.rerm.repository.UserRepository
import com.huytran.rerm.service.core.BaseService

class TestService(repository: UserRepository) : BaseService<User, UserRepository>(repository) {
    override fun createModel(): User {
        return User()
    }

    override fun parseParams(model: User) {

    }
}
package com.huytran.rerm.service

import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.User
import com.huytran.rerm.model.UserDetail
import com.huytran.rerm.repository.UserDetailRepository
import com.huytran.rerm.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val userDetailRepository: UserDetailRepository) {

    fun create(
            username: String,
            password: String,
            name: String,
            address: String,
            phoneNumber: String): BeanBasic {
        val existedUser = userRepository.findByName(username)
        if (existedUser.isPresent) {
            return BeanBasic()
        }

        val user = User()
        user.name = username
        user.password = password

        val userDetail = UserDetail()
        userDetail.name = name
        userDetail.address = address
        userDetail.phoneNumber = phoneNumber

        user.userDetail = userDetail
        userDetail.user = user

        userRepository.save(user)
        userDetailRepository.save(userDetail)

        return user.createBean()
    }

    fun delete(
            userId: Long) {
        val user = userRepository.findById(userId)
        if (!user.isPresent) {
            return
        }

        user.get().userDetail?.let {
            userDetailRepository.delete(it)
        }

        userRepository.delete(user.get())
    }

    fun getNativeDetail(): List<Any> {
        return userRepository.getNativeDetail()
    }

}
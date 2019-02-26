package com.huytran.rerm.service

import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.repository.UserDetailRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userDetailRepository: UserDetailRepository) {

    fun getAllUserDetail(): List<BeanBasic> {
        val userDetailList = userDetailRepository.findAll()
        return userDetailList.map {
            it.createBean()
        }
    }

    fun getWithPaging(
            page: Int
    ): List<BeanBasic> {
        val pageRequestParam =  PageRequest.of(
                page,
                2,
                Sort(
                        Sort.Direction.ASC,
                        "id"
                )
        )
        val pageResult = userDetailRepository.findAll(pageRequestParam)
        return pageResult.content.map {
            it.createBean()
        }
    }

    fun updateInfo(
            userId: Long,
            name: String,
            address: String,
            phoneNumber: String): BeanBasic {
        val originalUserDetail = userDetailRepository.findByUser_Id(userId)
        if (!originalUserDetail.isPresent) {
            return BeanBasic()
        }

        val userDetail = originalUserDetail.get()
        userDetail.tsLastModified = System.currentTimeMillis()

        userDetail.name = name
        userDetail.address = address
        userDetail.phoneNumber = phoneNumber
        userDetailRepository.save(userDetail)

        return userDetail.createBean()
    }

}
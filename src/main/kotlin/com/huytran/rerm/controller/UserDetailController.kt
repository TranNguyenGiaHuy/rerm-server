package com.huytran.rerm.controller

import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.service.UserDetailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/userDetail")
class UserDetailController(private val userDetailService: UserDetailService) {

    @PostMapping("/getAll")
    fun getAllUserDetail(): List<BeanBasic> {
        return userDetailService.getAllUserDetail()
    }

    @PostMapping("/updateInfo")
    fun updateInfo(
            @RequestParam(name = "userId") userId: Long,
            @RequestParam(name = "name") name: String,
            @RequestParam(name = "address") address: String,
            @RequestParam(name = "phoneNumber") phoneNumber: String): BeanBasic {
        return userDetailService.updateInfo(
                userId,
                name,
                address,
                phoneNumber
        )
    }

    @PostMapping("/getWithPaging")
    fun getWithPaging(
            @RequestParam(name = "page") page: Int): List<BeanBasic> {
        return userDetailService.getWithPaging(
                page
        )
    }

}
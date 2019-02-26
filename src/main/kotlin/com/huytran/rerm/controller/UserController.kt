package com.huytran.rerm.controller

import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun create(
            @RequestParam(name = "username") username: String,
            @RequestParam(name = "password") password: String,
            @RequestParam(name = "name") name: String,
            @RequestParam(name = "address") address: String,
            @RequestParam(name = "phoneNumber") phoneNumber: String): BeanBasic {
        return userService.create(
                username,
                password,
                name,
                address,
                phoneNumber
        )
    }

    @PostMapping("/delete")
    fun delete(
            @RequestParam(name = "id") id: Long) {
        userService.delete(id)
    }

    @PostMapping("/getNativeDetail")
    fun getNativeDetail(): List<Any> {
        return userService.getNativeDetail()
    }

}
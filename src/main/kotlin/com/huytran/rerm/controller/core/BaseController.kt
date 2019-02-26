package com.huytran.rerm.controller.core

import com.huytran.rerm.bean.core.BeanResult
import com.huytran.rerm.model.core.ModelCore
import com.huytran.rerm.service.core.BaseService
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.PostMapping

class BaseController<Service : BaseService<ModelCore, CrudRepository<ModelCore, Long>>>(private val service: Service) {

    @PostMapping("/create")
    fun create(): BeanResult {
        return service.create()
    }

    @PostMapping("/update")
    fun update(id: Long): BeanResult {
        return service.update(id)
    }

    @PostMapping("/delete")
    fun delete(id: Long) {
        service.delete(id)
    }

    @PostMapping("/get")
    fun get(id: Long): BeanResult {
        return service.get(id)
    }

    @PostMapping("/getAll")
    fun getAll(): BeanResult {
        return service.getAll()
    }

}
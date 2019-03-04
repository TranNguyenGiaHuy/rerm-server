package com.huytran.rerm.controller.core

import com.huytran.rerm.bean.core.BeanResult
import com.huytran.rerm.model.core.ModelCore
import com.huytran.rerm.service.core.BaseService
import org.springframework.data.repository.CrudRepository
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid

abstract class BaseController<out Service : BaseService<ModelCore, CrudRepository<ModelCore, Long>, BaseService.AbstractParams>, Params : BaseService.AbstractParams>(private val service: Service) {

    @PostMapping("/create")
    open fun create(@Valid params: Params): BeanResult {
        return service.create(params)
    }

    @PostMapping("/update")
    open fun update(
            @RequestParam("id") id: Long,
            @Valid params: Params): BeanResult {
        return service.update(id, params)
    }

    @PostMapping("/delete")
    open fun delete(@RequestParam("id") id: Long) {
        service.delete(id)
    }

    @PostMapping("/get")
    open fun get(@RequestParam("id") id: Long): BeanResult {
        return service.get(id)
    }

    @PostMapping("/getAll")
    open fun getAll(): BeanResult {
        return service.getAll()
    }

}
package com.huytran.rerm.service.core

import com.huytran.rerm.bean.core.BeanList
import com.huytran.rerm.bean.core.BeanResult
import com.huytran.rerm.constant.ResultCode
import com.huytran.rerm.model.core.ModelCore
import org.springframework.data.repository.CrudRepository

abstract class BaseService<BaseModel : ModelCore, out BaseRepository : CrudRepository<BaseModel, Long>>(private val repository: BaseRepository) {

    abstract fun createModel(): BaseModel
    abstract fun parseParams(model: BaseModel)

    fun create(): BeanResult {
        val beanResult = BeanResult()

        val model = createModel()
        parseParams(model)
        repository.save(model)
        beanResult.bean = model.createBean()
        beanResult.code = ResultCode.RESULT_CODE_VALID

        return beanResult
    }

    fun update(id: Long): BeanResult {
        val beanResult = BeanResult()

        val optional = repository.findById(id)
        if (!optional.isPresent) {
            beanResult.code = ResultCode.RESULT_CODE_NOT_FOUND
            return beanResult
        }

        val model = optional.get()
        parseParams(model as BaseModel)
        repository.save(model)

        beanResult.bean = model.createBean()
        beanResult.code = ResultCode.RESULT_CODE_VALID
        return beanResult
    }

    fun delete(id: Long): BeanResult {
        val beanResult = BeanResult()

        val optional = repository.findById(id)
        if (!optional.isPresent) {
            beanResult.code = ResultCode.RESULT_CODE_NOT_FOUND
            return beanResult
        }

        val model = optional.get()
        repository.delete(model)

        beanResult.code = ResultCode.RESULT_CODE_VALID
        return beanResult
    }

    fun get(id: Long): BeanResult {
        val beanResult = BeanResult()

        val optional = repository.findById(id)
        if (!optional.isPresent) {
            beanResult.code = ResultCode.RESULT_CODE_NOT_FOUND
            return beanResult
        }

        beanResult.bean = optional.get().createBean()
        beanResult.code = ResultCode.RESULT_CODE_VALID
        return beanResult
    }

    fun getAll(): BeanResult {
        val beanResult = BeanResult()

        val modelList = repository.findAll()

        beanResult.bean = BeanList(modelList)
        beanResult.code = ResultCode.RESULT_CODE_VALID
        return beanResult
    }

}
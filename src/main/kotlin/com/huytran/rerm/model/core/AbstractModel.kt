package com.huytran.rerm.model.core

import com.huytran.rerm.bean.core.BeanBasic

abstract class AbstractModel {

    open fun createEmptyBean(): BeanBasic {
        return BeanBasic()
    }

    fun createBean() : BeanBasic {
        val bean = createEmptyBean()
        parseToBean(bean)
        return bean
    }

    abstract fun parseToBean(beanBasic: BeanBasic)

}
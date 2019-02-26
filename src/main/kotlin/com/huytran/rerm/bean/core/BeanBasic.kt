package com.huytran.rerm.bean.core

import java.io.Serializable

open class BeanBasic : Serializable, BeanCore() {

    init {
        beanType = "BeanBasic"
    }

    open var id: Long = 0
    var tsCreated: Long = 0
    var tsLastModified: Long = 0
}
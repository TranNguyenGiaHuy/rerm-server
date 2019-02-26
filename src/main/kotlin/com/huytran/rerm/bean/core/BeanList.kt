package com.huytran.rerm.bean.core

import com.huytran.rerm.model.core.ModelCore

class BeanList(listModel: Iterable<ModelCore>) : BeanBasic() {

    init {
        beanType = "BeanList"
    }

    var listBean = emptyList<BeanBasic>()

    init {
        listBean = listModel.map {
            it.createBean()
        }
    }

}
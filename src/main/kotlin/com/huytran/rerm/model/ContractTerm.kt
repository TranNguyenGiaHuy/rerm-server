package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanContractTerm
import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "contract_term")
data class ContractTerm(
        @Column(name = "name")
        var name: String = "",
        @Column(name = "description")
        var description: String = ""
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanContractTerm()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanContractTerm
        trueBean.name = this.name
        trueBean.description = this.description
    }
}
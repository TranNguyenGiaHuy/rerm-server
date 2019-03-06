package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanContractContractTerm
import com.huytran.rerm.bean.BeanContractTerm
import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "contract_contract_term")
data class ContractContractTerm(
        @Column(name = "contract_id")
        var contractId: Long = -1,
        @Column(name = "contract_term_id")
        var contractTermId: Long = -1
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanContractContractTerm()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanContractContractTerm
        trueBean.contractId = this.contractId
        trueBean.contractTermId = this.contractTermId
    }
}
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
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) @JoinColumn(name = "contract_id")
        var contract: Contract? = null,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) @JoinColumn(name = "contract_term_id")
        var contractTerm: ContractTerm? = null
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanContractContractTerm()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanContractContractTerm
        trueBean.contractId = this.contract?.id ?: -1
        trueBean.contractTermId = this.contractTerm?.id ?: -1
    }
}
package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanTransaction
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "transaction")
data class Transaction(
        @Column(name = "blockchain_hash")
        var blockchainHash: String = "",
        @Column(name = "from")
        var from: String = "",
        @Column(name = "to")
        var to: String = "",
        @Column(name = "blockchain_fee")
        var blockchainFee: Double = 0.0,
        @Column(name = "status")
        var status: Long = -1,
        @Column(name = "type")
        var type: Long = -1
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanTransaction()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanTransaction
        trueBean.blockchainHash = this.blockchainHash
        trueBean.from = this.from
        trueBean.to = this.to
        trueBean.blockchainFee = this.blockchainFee
        trueBean.status = this.status
        trueBean.type = this.type
    }
}
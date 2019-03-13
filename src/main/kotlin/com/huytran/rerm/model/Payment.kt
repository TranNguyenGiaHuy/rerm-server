package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanPayment
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "payment")
data class Payment(
        @Column(name = "amount")
        var amount: Float = 0f,
        @Column(name = "currency")
        var currency: String = "",
        @Column(name = "src")
        var src: Long = -1,
        @Column(name = "des")
        var des: Long = -1,
        @Column(name = "transaction_id")
        var transactionId: Long = -1
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanPayment()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanPayment
        trueBean.amount = this.amount
        trueBean.currency = this.currency
        trueBean.src = this.src
        trueBean.des = this.des
        trueBean.transactionId = this.transactionId
    }
}
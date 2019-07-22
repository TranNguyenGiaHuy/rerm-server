package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanPayment
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "payment")
data class Payment(
        @Column(name = "amount")
        var amount: Long = 0L,
        @Column(name = "currency")
        var currency: String = "",
//        @Column(name = "src")
//        var src: Long = -1,
//        @Column(name = "des")
//        var des: Long = -1,
        @Column(name = "status")
        var status: Int = 0,
        @Column(name = "ts_start")
        var tsStart: Long = -1,
        @Column(name = "ts_end")
        var tsEnd: Long = -1,
        @Column(name = "electricity_bill")
        var electricityBill: Long = 0,
        @Column(name = "water_bill")
        var waterBill: Long = 0,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "payer")
        var payer: User? = null,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "contract")
        var contract: Contract? = null
//        @OneToOne
//        @JoinColumn(name = "transaction_id")
//        var transaction: Transaction? = null
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanPayment()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanPayment
        trueBean.amount = this.amount
        trueBean.currency = this.currency
//        trueBean.src = this.src
//        trueBean.des = this.des
//        trueBean.transactionId = this.transaction?.id ?: -1
        trueBean.status = this.status
        trueBean.payerId = this.payer?.id ?: -1
        trueBean.contractId = this.contract?.id ?: -1
        trueBean.tsStart = this.tsStart
        trueBean.tsEnd = this.tsEnd
        trueBean.electricityBill = this.electricityBill
        trueBean.waterBill = this.waterBill
    }
}
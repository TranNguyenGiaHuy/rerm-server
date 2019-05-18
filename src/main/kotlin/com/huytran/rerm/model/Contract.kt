package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanContract
import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "contract")
data class Contract(
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner")
        var owner: User? = null,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "renter")
        var renter: User? = null,
        @Column(name = "ts_start")
        var tsStart: Long = -1,
        @Column(name = "ts_end")
        var tsEnd: Long = -1,
        @Column(name = "prepaid")
        var prepaid: Long = -1,
        @Column(name = "mode_of_payment")
        var modeOPayment: Int = -1,
        @Column(name = "term")
        var term: String = "",
        @Column(name = "number_of_room")
        var numberOfRoom: Long = -1,
        @Column(name = "transaction_id")
        var transactionId: Long = -1,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "room_id")
        var room: Room? = null
//        @OneToMany(mappedBy = "contract" ,fetch = FetchType.LAZY)
//        var contractContractTermList: List<ContractContractTerm> = emptyList()

) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanContract()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanContract
        trueBean.owner = this.owner?.id ?: -1
        trueBean.renter = this.renter?.id ?: -1
        trueBean.tsStart = this.tsStart
        trueBean.tsEnd = this.tsEnd
        trueBean.prepaid = this.prepaid
        trueBean.modeOPayment = this.modeOPayment
        trueBean.numberOfRoom = this.numberOfRoom
        trueBean.transactionId = this.transactionId
        trueBean.term = this.term
        trueBean.roomId = this.room?.id ?: -1
    }
}
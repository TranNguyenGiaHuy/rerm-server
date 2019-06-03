package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanContract
import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanRentRequest
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "rent_request")
data class RentRequest(
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "renter")
        var renter: User? = null,
        @Column(name = "ts_start")
        var tsStart: Long = -1,
        @Column(name = "ts_end")
        var tsEnd: Long = -1,
        @Column(name = "is_confirm")
        var isConfirm: Boolean = false,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "room_id")
        var room: Room? = null

) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanRentRequest()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanRentRequest
        trueBean.renter = this.renter?.id ?: -1
        trueBean.tsStart = this.tsStart
        trueBean.tsEnd = this.tsEnd
        trueBean.roomId = this.room?.id ?: -1
    }
}
package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanUserDetail
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "user_detail")
data class UserDetail(
        @Column(name = "name")
        var name: String,
        @Column(name = "address")
        var address: String,
        @Column(name = "phone_number")
        var phoneNumber: String,
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User?
) : ModelCore() {

    constructor() : this("", "", "", null)

    override fun createEmptyBean(): BeanBasic {
        return BeanUserDetail()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanUserDetail
        trueBean.name = this.name
        trueBean.address = this.address
        trueBean.phoneNumber = this.phoneNumber
    }
}
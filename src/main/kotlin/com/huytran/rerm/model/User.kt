package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "user")
data class User(
        @Column(name = "name")
        var name: String,
        @Column(name = "password")
        var password: String,
        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "user")
        var userDetail: UserDetail?
) : ModelCore() {

    constructor() : this("", "", null)

    override fun createEmptyBean(): BeanBasic {
        return BeanUser()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanUser
        trueBean.name = this.name
    }
}
package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanGrpcSession
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "grpc_session")
data class GrpcSession(
        @Column(name = "user_id")
        var userId: Long = 0,
        @Column(name = "address")
        var token: String = "") : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanGrpcSession()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val bean = beanBasic as BeanGrpcSession

        bean.userId = this.userId
        bean.token = this.token
    }
}
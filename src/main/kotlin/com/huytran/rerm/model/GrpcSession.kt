package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanGrpcSession
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "grpc_session")
data class GrpcSession(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User? = null,
        @Column(name = "token", unique = true)
        var token: String = "") : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanGrpcSession()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val bean = beanBasic as BeanGrpcSession

        bean.userId = this.user?.id ?: -1
        bean.token = this.token
    }
}
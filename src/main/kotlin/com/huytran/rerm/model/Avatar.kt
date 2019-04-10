package com.huytran.rerm.model

import com.huytran.rerm.bean.BeanAvatar
import com.huytran.rerm.bean.BeanImage
import com.huytran.rerm.bean.BeanUser
import com.huytran.rerm.bean.core.BeanBasic
import com.huytran.rerm.model.core.ModelCore
import javax.persistence.*

@Entity(name = "avatar")
data class Avatar(
        @Column(name = "path")
        var path: String = "",
        @OneToOne
        @JoinColumn(name = "user_id")
        var user: User? = null,
        @Column(name = "name")
        var name: String = ""
) : ModelCore() {

    override fun createEmptyBean(): BeanBasic {
        return BeanAvatar()
    }

    override fun parseToBean(beanBasic: BeanBasic) {
        super.parseToBean(beanBasic)
        val trueBean = beanBasic as BeanAvatar
        trueBean.userId = this.user?.id ?: -1
    }
}